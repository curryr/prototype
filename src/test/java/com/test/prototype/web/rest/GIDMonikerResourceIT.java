package com.test.prototype.web.rest;

import com.test.prototype.PrototypeApp;
import com.test.prototype.domain.GIDMoniker;
import com.test.prototype.repository.GIDMonikerRepository;

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

import com.test.prototype.domain.enumeration.GIDMonikerPrefix;
/**
 * Integration tests for the {@link GIDMonikerResource} REST controller.
 */
@SpringBootTest(classes = PrototypeApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class GIDMonikerResourceIT {

    private static final String DEFAULT_MONIKER = "AAAAAAAAAA";
    private static final String UPDATED_MONIKER = "BBBBBBBBBB";

    private static final GIDMonikerPrefix DEFAULT_PREFIX = GIDMonikerPrefix.LEGACY;
    private static final GIDMonikerPrefix UPDATED_PREFIX = GIDMonikerPrefix.TEL;

    @Autowired
    private GIDMonikerRepository gIDMonikerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGIDMonikerMockMvc;

    private GIDMoniker gIDMoniker;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GIDMoniker createEntity(EntityManager em) {
        GIDMoniker gIDMoniker = new GIDMoniker()
            .moniker(DEFAULT_MONIKER)
            .prefix(DEFAULT_PREFIX);
        return gIDMoniker;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GIDMoniker createUpdatedEntity(EntityManager em) {
        GIDMoniker gIDMoniker = new GIDMoniker()
            .moniker(UPDATED_MONIKER)
            .prefix(UPDATED_PREFIX);
        return gIDMoniker;
    }

    @BeforeEach
    public void initTest() {
        gIDMoniker = createEntity(em);
    }

    @Test
    @Transactional
    public void createGIDMoniker() throws Exception {
        int databaseSizeBeforeCreate = gIDMonikerRepository.findAll().size();

        // Create the GIDMoniker
        restGIDMonikerMockMvc.perform(post("/api/gid-monikers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gIDMoniker)))
            .andExpect(status().isCreated());

        // Validate the GIDMoniker in the database
        List<GIDMoniker> gIDMonikerList = gIDMonikerRepository.findAll();
        assertThat(gIDMonikerList).hasSize(databaseSizeBeforeCreate + 1);
        GIDMoniker testGIDMoniker = gIDMonikerList.get(gIDMonikerList.size() - 1);
        assertThat(testGIDMoniker.getMoniker()).isEqualTo(DEFAULT_MONIKER);
        assertThat(testGIDMoniker.getPrefix()).isEqualTo(DEFAULT_PREFIX);
    }

    @Test
    @Transactional
    public void createGIDMonikerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gIDMonikerRepository.findAll().size();

        // Create the GIDMoniker with an existing ID
        gIDMoniker.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGIDMonikerMockMvc.perform(post("/api/gid-monikers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gIDMoniker)))
            .andExpect(status().isBadRequest());

        // Validate the GIDMoniker in the database
        List<GIDMoniker> gIDMonikerList = gIDMonikerRepository.findAll();
        assertThat(gIDMonikerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGIDMonikers() throws Exception {
        // Initialize the database
        gIDMonikerRepository.saveAndFlush(gIDMoniker);

        // Get all the gIDMonikerList
        restGIDMonikerMockMvc.perform(get("/api/gid-monikers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gIDMoniker.getId().intValue())))
            .andExpect(jsonPath("$.[*].moniker").value(hasItem(DEFAULT_MONIKER)))
            .andExpect(jsonPath("$.[*].prefix").value(hasItem(DEFAULT_PREFIX.toString())));
    }
    
    @Test
    @Transactional
    public void getGIDMoniker() throws Exception {
        // Initialize the database
        gIDMonikerRepository.saveAndFlush(gIDMoniker);

        // Get the gIDMoniker
        restGIDMonikerMockMvc.perform(get("/api/gid-monikers/{id}", gIDMoniker.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gIDMoniker.getId().intValue()))
            .andExpect(jsonPath("$.moniker").value(DEFAULT_MONIKER))
            .andExpect(jsonPath("$.prefix").value(DEFAULT_PREFIX.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGIDMoniker() throws Exception {
        // Get the gIDMoniker
        restGIDMonikerMockMvc.perform(get("/api/gid-monikers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGIDMoniker() throws Exception {
        // Initialize the database
        gIDMonikerRepository.saveAndFlush(gIDMoniker);

        int databaseSizeBeforeUpdate = gIDMonikerRepository.findAll().size();

        // Update the gIDMoniker
        GIDMoniker updatedGIDMoniker = gIDMonikerRepository.findById(gIDMoniker.getId()).get();
        // Disconnect from session so that the updates on updatedGIDMoniker are not directly saved in db
        em.detach(updatedGIDMoniker);
        updatedGIDMoniker
            .moniker(UPDATED_MONIKER)
            .prefix(UPDATED_PREFIX);

        restGIDMonikerMockMvc.perform(put("/api/gid-monikers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGIDMoniker)))
            .andExpect(status().isOk());

        // Validate the GIDMoniker in the database
        List<GIDMoniker> gIDMonikerList = gIDMonikerRepository.findAll();
        assertThat(gIDMonikerList).hasSize(databaseSizeBeforeUpdate);
        GIDMoniker testGIDMoniker = gIDMonikerList.get(gIDMonikerList.size() - 1);
        assertThat(testGIDMoniker.getMoniker()).isEqualTo(UPDATED_MONIKER);
        assertThat(testGIDMoniker.getPrefix()).isEqualTo(UPDATED_PREFIX);
    }

    @Test
    @Transactional
    public void updateNonExistingGIDMoniker() throws Exception {
        int databaseSizeBeforeUpdate = gIDMonikerRepository.findAll().size();

        // Create the GIDMoniker

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGIDMonikerMockMvc.perform(put("/api/gid-monikers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gIDMoniker)))
            .andExpect(status().isBadRequest());

        // Validate the GIDMoniker in the database
        List<GIDMoniker> gIDMonikerList = gIDMonikerRepository.findAll();
        assertThat(gIDMonikerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGIDMoniker() throws Exception {
        // Initialize the database
        gIDMonikerRepository.saveAndFlush(gIDMoniker);

        int databaseSizeBeforeDelete = gIDMonikerRepository.findAll().size();

        // Delete the gIDMoniker
        restGIDMonikerMockMvc.perform(delete("/api/gid-monikers/{id}", gIDMoniker.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GIDMoniker> gIDMonikerList = gIDMonikerRepository.findAll();
        assertThat(gIDMonikerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
