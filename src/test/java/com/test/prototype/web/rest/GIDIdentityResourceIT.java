package com.test.prototype.web.rest;

import com.test.prototype.PrototypeApp;
import com.test.prototype.domain.GIDIdentity;
import com.test.prototype.repository.GIDIdentityRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GIDIdentityResource} REST controller.
 */
@SpringBootTest(classes = PrototypeApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class GIDIdentityResourceIT {

    private static final String DEFAULT_GID = "AAAAAAAAAA";
    private static final String UPDATED_GID = "BBBBBBBBBB";

    private static final String DEFAULT_PGID = "AAAAAAAAAA";
    private static final String UPDATED_PGID = "BBBBBBBBBB";

    @Autowired
    private GIDIdentityRepository gIDIdentityRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGIDIdentityMockMvc;

    private GIDIdentity gIDIdentity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GIDIdentity createEntity(EntityManager em) {
        GIDIdentity gIDIdentity = new GIDIdentity()
            .gid(DEFAULT_GID)
            .pgid(DEFAULT_PGID);
        return gIDIdentity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GIDIdentity createUpdatedEntity(EntityManager em) {
        GIDIdentity gIDIdentity = new GIDIdentity()
            .gid(UPDATED_GID)
            .pgid(UPDATED_PGID);
        return gIDIdentity;
    }

    @BeforeEach
    public void initTest() {
        gIDIdentity = createEntity(em);
    }

    @Test
    @Transactional
    public void createGIDIdentity() throws Exception {
        int databaseSizeBeforeCreate = gIDIdentityRepository.findAll().size();

        // Create the GIDIdentity
        restGIDIdentityMockMvc.perform(post("/api/gid-identities").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gIDIdentity)))
            .andExpect(status().isCreated());

        // Validate the GIDIdentity in the database
        List<GIDIdentity> gIDIdentityList = gIDIdentityRepository.findAll();
        assertThat(gIDIdentityList).hasSize(databaseSizeBeforeCreate + 1);
        GIDIdentity testGIDIdentity = gIDIdentityList.get(gIDIdentityList.size() - 1);
        assertThat(testGIDIdentity.getGid()).isEqualTo(DEFAULT_GID);
        assertThat(testGIDIdentity.getPgid()).isEqualTo(DEFAULT_PGID);
    }

    @Test
    @Transactional
    public void createGIDIdentityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gIDIdentityRepository.findAll().size();

        // Create the GIDIdentity with an existing ID
        gIDIdentity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGIDIdentityMockMvc.perform(post("/api/gid-identities").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gIDIdentity)))
            .andExpect(status().isBadRequest());

        // Validate the GIDIdentity in the database
        List<GIDIdentity> gIDIdentityList = gIDIdentityRepository.findAll();
        assertThat(gIDIdentityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGIDIdentities() throws Exception {
        // Initialize the database
        gIDIdentityRepository.saveAndFlush(gIDIdentity);

        // Get all the gIDIdentityList
        restGIDIdentityMockMvc.perform(get("/api/gid-identities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gIDIdentity.getId().intValue())))
            .andExpect(jsonPath("$.[*].gid").value(hasItem(DEFAULT_GID)))
            .andExpect(jsonPath("$.[*].pgid").value(hasItem(DEFAULT_PGID)));
    }
    
    @Test
    @Transactional
    public void getGIDIdentity() throws Exception {
        // Initialize the database
        gIDIdentityRepository.saveAndFlush(gIDIdentity);

        // Get the gIDIdentity
        restGIDIdentityMockMvc.perform(get("/api/gid-identities/{id}", gIDIdentity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gIDIdentity.getId().intValue()))
            .andExpect(jsonPath("$.gid").value(DEFAULT_GID))
            .andExpect(jsonPath("$.pgid").value(DEFAULT_PGID));
    }

    @Test
    @Transactional
    public void getNonExistingGIDIdentity() throws Exception {
        // Get the gIDIdentity
        restGIDIdentityMockMvc.perform(get("/api/gid-identities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGIDIdentity() throws Exception {
        // Initialize the database
        gIDIdentityRepository.saveAndFlush(gIDIdentity);

        int databaseSizeBeforeUpdate = gIDIdentityRepository.findAll().size();

        // Update the gIDIdentity
        GIDIdentity updatedGIDIdentity = gIDIdentityRepository.findById(gIDIdentity.getId()).get();
        // Disconnect from session so that the updates on updatedGIDIdentity are not directly saved in db
        em.detach(updatedGIDIdentity);
        updatedGIDIdentity
            .gid(UPDATED_GID)
            .pgid(UPDATED_PGID);

        restGIDIdentityMockMvc.perform(put("/api/gid-identities").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGIDIdentity)))
            .andExpect(status().isOk());

        // Validate the GIDIdentity in the database
        List<GIDIdentity> gIDIdentityList = gIDIdentityRepository.findAll();
        assertThat(gIDIdentityList).hasSize(databaseSizeBeforeUpdate);
        GIDIdentity testGIDIdentity = gIDIdentityList.get(gIDIdentityList.size() - 1);
        assertThat(testGIDIdentity.getGid()).isEqualTo(UPDATED_GID);
        assertThat(testGIDIdentity.getPgid()).isEqualTo(UPDATED_PGID);
    }

    @Test
    @Transactional
    public void updateNonExistingGIDIdentity() throws Exception {
        int databaseSizeBeforeUpdate = gIDIdentityRepository.findAll().size();

        // Create the GIDIdentity

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGIDIdentityMockMvc.perform(put("/api/gid-identities").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gIDIdentity)))
            .andExpect(status().isBadRequest());

        // Validate the GIDIdentity in the database
        List<GIDIdentity> gIDIdentityList = gIDIdentityRepository.findAll();
        assertThat(gIDIdentityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGIDIdentity() throws Exception {
        // Initialize the database
        gIDIdentityRepository.saveAndFlush(gIDIdentity);

        int databaseSizeBeforeDelete = gIDIdentityRepository.findAll().size();

        // Delete the gIDIdentity
        restGIDIdentityMockMvc.perform(delete("/api/gid-identities/{id}", gIDIdentity.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GIDIdentity> gIDIdentityList = gIDIdentityRepository.findAll();
        assertThat(gIDIdentityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
