package com.test.prototype.web.rest;

import com.test.prototype.PrototypeApp;
import com.test.prototype.domain.GIDMembership;
import com.test.prototype.repository.GIDMembershipRepository;

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
 * Integration tests for the {@link GIDMembershipResource} REST controller.
 */
@SpringBootTest(classes = PrototypeApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class GIDMembershipResourceIT {

    private static final String DEFAULT_OGID = "AAAAAAAAAA";
    private static final String UPDATED_OGID = "BBBBBBBBBB";

    private static final String DEFAULT_TENANT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_TENANT_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_TENANT_USER_KEY = "AAAAAAAAAA";
    private static final String UPDATED_TENANT_USER_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_TENANT_USER_BLOCK = "AAAAAAAAAA";
    private static final String UPDATED_TENANT_USER_BLOCK = "BBBBBBBBBB";

    @Autowired
    private GIDMembershipRepository gIDMembershipRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGIDMembershipMockMvc;

    private GIDMembership gIDMembership;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GIDMembership createEntity(EntityManager em) {
        GIDMembership gIDMembership = new GIDMembership()
            .ogid(DEFAULT_OGID)
            .tenantKey(DEFAULT_TENANT_KEY)
            .tenantUserKey(DEFAULT_TENANT_USER_KEY)
            .tenantUserBlock(DEFAULT_TENANT_USER_BLOCK);
        return gIDMembership;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GIDMembership createUpdatedEntity(EntityManager em) {
        GIDMembership gIDMembership = new GIDMembership()
            .ogid(UPDATED_OGID)
            .tenantKey(UPDATED_TENANT_KEY)
            .tenantUserKey(UPDATED_TENANT_USER_KEY)
            .tenantUserBlock(UPDATED_TENANT_USER_BLOCK);
        return gIDMembership;
    }

    @BeforeEach
    public void initTest() {
        gIDMembership = createEntity(em);
    }

    @Test
    @Transactional
    public void createGIDMembership() throws Exception {
        int databaseSizeBeforeCreate = gIDMembershipRepository.findAll().size();

        // Create the GIDMembership
        restGIDMembershipMockMvc.perform(post("/api/gid-memberships").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gIDMembership)))
            .andExpect(status().isCreated());

        // Validate the GIDMembership in the database
        List<GIDMembership> gIDMembershipList = gIDMembershipRepository.findAll();
        assertThat(gIDMembershipList).hasSize(databaseSizeBeforeCreate + 1);
        GIDMembership testGIDMembership = gIDMembershipList.get(gIDMembershipList.size() - 1);
        assertThat(testGIDMembership.getOgid()).isEqualTo(DEFAULT_OGID);
        assertThat(testGIDMembership.getTenantKey()).isEqualTo(DEFAULT_TENANT_KEY);
        assertThat(testGIDMembership.getTenantUserKey()).isEqualTo(DEFAULT_TENANT_USER_KEY);
        assertThat(testGIDMembership.getTenantUserBlock()).isEqualTo(DEFAULT_TENANT_USER_BLOCK);
    }

    @Test
    @Transactional
    public void createGIDMembershipWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gIDMembershipRepository.findAll().size();

        // Create the GIDMembership with an existing ID
        gIDMembership.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGIDMembershipMockMvc.perform(post("/api/gid-memberships").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gIDMembership)))
            .andExpect(status().isBadRequest());

        // Validate the GIDMembership in the database
        List<GIDMembership> gIDMembershipList = gIDMembershipRepository.findAll();
        assertThat(gIDMembershipList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGIDMemberships() throws Exception {
        // Initialize the database
        gIDMembershipRepository.saveAndFlush(gIDMembership);

        // Get all the gIDMembershipList
        restGIDMembershipMockMvc.perform(get("/api/gid-memberships?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gIDMembership.getId().intValue())))
            .andExpect(jsonPath("$.[*].ogid").value(hasItem(DEFAULT_OGID)))
            .andExpect(jsonPath("$.[*].tenantKey").value(hasItem(DEFAULT_TENANT_KEY)))
            .andExpect(jsonPath("$.[*].tenantUserKey").value(hasItem(DEFAULT_TENANT_USER_KEY)))
            .andExpect(jsonPath("$.[*].tenantUserBlock").value(hasItem(DEFAULT_TENANT_USER_BLOCK)));
    }
    
    @Test
    @Transactional
    public void getGIDMembership() throws Exception {
        // Initialize the database
        gIDMembershipRepository.saveAndFlush(gIDMembership);

        // Get the gIDMembership
        restGIDMembershipMockMvc.perform(get("/api/gid-memberships/{id}", gIDMembership.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gIDMembership.getId().intValue()))
            .andExpect(jsonPath("$.ogid").value(DEFAULT_OGID))
            .andExpect(jsonPath("$.tenantKey").value(DEFAULT_TENANT_KEY))
            .andExpect(jsonPath("$.tenantUserKey").value(DEFAULT_TENANT_USER_KEY))
            .andExpect(jsonPath("$.tenantUserBlock").value(DEFAULT_TENANT_USER_BLOCK));
    }

    @Test
    @Transactional
    public void getNonExistingGIDMembership() throws Exception {
        // Get the gIDMembership
        restGIDMembershipMockMvc.perform(get("/api/gid-memberships/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGIDMembership() throws Exception {
        // Initialize the database
        gIDMembershipRepository.saveAndFlush(gIDMembership);

        int databaseSizeBeforeUpdate = gIDMembershipRepository.findAll().size();

        // Update the gIDMembership
        GIDMembership updatedGIDMembership = gIDMembershipRepository.findById(gIDMembership.getId()).get();
        // Disconnect from session so that the updates on updatedGIDMembership are not directly saved in db
        em.detach(updatedGIDMembership);
        updatedGIDMembership
            .ogid(UPDATED_OGID)
            .tenantKey(UPDATED_TENANT_KEY)
            .tenantUserKey(UPDATED_TENANT_USER_KEY)
            .tenantUserBlock(UPDATED_TENANT_USER_BLOCK);

        restGIDMembershipMockMvc.perform(put("/api/gid-memberships").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGIDMembership)))
            .andExpect(status().isOk());

        // Validate the GIDMembership in the database
        List<GIDMembership> gIDMembershipList = gIDMembershipRepository.findAll();
        assertThat(gIDMembershipList).hasSize(databaseSizeBeforeUpdate);
        GIDMembership testGIDMembership = gIDMembershipList.get(gIDMembershipList.size() - 1);
        assertThat(testGIDMembership.getOgid()).isEqualTo(UPDATED_OGID);
        assertThat(testGIDMembership.getTenantKey()).isEqualTo(UPDATED_TENANT_KEY);
        assertThat(testGIDMembership.getTenantUserKey()).isEqualTo(UPDATED_TENANT_USER_KEY);
        assertThat(testGIDMembership.getTenantUserBlock()).isEqualTo(UPDATED_TENANT_USER_BLOCK);
    }

    @Test
    @Transactional
    public void updateNonExistingGIDMembership() throws Exception {
        int databaseSizeBeforeUpdate = gIDMembershipRepository.findAll().size();

        // Create the GIDMembership

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGIDMembershipMockMvc.perform(put("/api/gid-memberships").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gIDMembership)))
            .andExpect(status().isBadRequest());

        // Validate the GIDMembership in the database
        List<GIDMembership> gIDMembershipList = gIDMembershipRepository.findAll();
        assertThat(gIDMembershipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGIDMembership() throws Exception {
        // Initialize the database
        gIDMembershipRepository.saveAndFlush(gIDMembership);

        int databaseSizeBeforeDelete = gIDMembershipRepository.findAll().size();

        // Delete the gIDMembership
        restGIDMembershipMockMvc.perform(delete("/api/gid-memberships/{id}", gIDMembership.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GIDMembership> gIDMembershipList = gIDMembershipRepository.findAll();
        assertThat(gIDMembershipList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
