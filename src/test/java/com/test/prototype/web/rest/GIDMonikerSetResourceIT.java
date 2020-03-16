package com.test.prototype.web.rest;

import com.test.prototype.PrototypeApp;
import com.test.prototype.domain.GIDMonikerSet;
import com.test.prototype.repository.GIDMonikerSetRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GIDMonikerSetResource} REST controller.
 */
@SpringBootTest(classes = PrototypeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class GIDMonikerSetResourceIT {

    @Autowired
    private GIDMonikerSetRepository gIDMonikerSetRepository;

    @Mock
    private GIDMonikerSetRepository gIDMonikerSetRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGIDMonikerSetMockMvc;

    private GIDMonikerSet gIDMonikerSet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GIDMonikerSet createEntity(EntityManager em) {
        GIDMonikerSet gIDMonikerSet = new GIDMonikerSet();
        return gIDMonikerSet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GIDMonikerSet createUpdatedEntity(EntityManager em) {
        GIDMonikerSet gIDMonikerSet = new GIDMonikerSet();
        return gIDMonikerSet;
    }

    @BeforeEach
    public void initTest() {
        gIDMonikerSet = createEntity(em);
    }

    @Test
    @Transactional
    public void createGIDMonikerSet() throws Exception {
        int databaseSizeBeforeCreate = gIDMonikerSetRepository.findAll().size();

        // Create the GIDMonikerSet
        restGIDMonikerSetMockMvc.perform(post("/api/gid-moniker-sets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gIDMonikerSet)))
            .andExpect(status().isCreated());

        // Validate the GIDMonikerSet in the database
        List<GIDMonikerSet> gIDMonikerSetList = gIDMonikerSetRepository.findAll();
        assertThat(gIDMonikerSetList).hasSize(databaseSizeBeforeCreate + 1);
        GIDMonikerSet testGIDMonikerSet = gIDMonikerSetList.get(gIDMonikerSetList.size() - 1);
    }

    @Test
    @Transactional
    public void createGIDMonikerSetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gIDMonikerSetRepository.findAll().size();

        // Create the GIDMonikerSet with an existing ID
        gIDMonikerSet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGIDMonikerSetMockMvc.perform(post("/api/gid-moniker-sets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gIDMonikerSet)))
            .andExpect(status().isBadRequest());

        // Validate the GIDMonikerSet in the database
        List<GIDMonikerSet> gIDMonikerSetList = gIDMonikerSetRepository.findAll();
        assertThat(gIDMonikerSetList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGIDMonikerSets() throws Exception {
        // Initialize the database
        gIDMonikerSetRepository.saveAndFlush(gIDMonikerSet);

        // Get all the gIDMonikerSetList
        restGIDMonikerSetMockMvc.perform(get("/api/gid-moniker-sets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gIDMonikerSet.getId().intValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllGIDMonikerSetsWithEagerRelationshipsIsEnabled() throws Exception {
        GIDMonikerSetResource gIDMonikerSetResource = new GIDMonikerSetResource(gIDMonikerSetRepositoryMock);
        when(gIDMonikerSetRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGIDMonikerSetMockMvc.perform(get("/api/gid-moniker-sets?eagerload=true"))
            .andExpect(status().isOk());

        verify(gIDMonikerSetRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllGIDMonikerSetsWithEagerRelationshipsIsNotEnabled() throws Exception {
        GIDMonikerSetResource gIDMonikerSetResource = new GIDMonikerSetResource(gIDMonikerSetRepositoryMock);
        when(gIDMonikerSetRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGIDMonikerSetMockMvc.perform(get("/api/gid-moniker-sets?eagerload=true"))
            .andExpect(status().isOk());

        verify(gIDMonikerSetRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getGIDMonikerSet() throws Exception {
        // Initialize the database
        gIDMonikerSetRepository.saveAndFlush(gIDMonikerSet);

        // Get the gIDMonikerSet
        restGIDMonikerSetMockMvc.perform(get("/api/gid-moniker-sets/{id}", gIDMonikerSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gIDMonikerSet.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingGIDMonikerSet() throws Exception {
        // Get the gIDMonikerSet
        restGIDMonikerSetMockMvc.perform(get("/api/gid-moniker-sets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGIDMonikerSet() throws Exception {
        // Initialize the database
        gIDMonikerSetRepository.saveAndFlush(gIDMonikerSet);

        int databaseSizeBeforeUpdate = gIDMonikerSetRepository.findAll().size();

        // Update the gIDMonikerSet
        GIDMonikerSet updatedGIDMonikerSet = gIDMonikerSetRepository.findById(gIDMonikerSet.getId()).get();
        // Disconnect from session so that the updates on updatedGIDMonikerSet are not directly saved in db
        em.detach(updatedGIDMonikerSet);

        restGIDMonikerSetMockMvc.perform(put("/api/gid-moniker-sets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGIDMonikerSet)))
            .andExpect(status().isOk());

        // Validate the GIDMonikerSet in the database
        List<GIDMonikerSet> gIDMonikerSetList = gIDMonikerSetRepository.findAll();
        assertThat(gIDMonikerSetList).hasSize(databaseSizeBeforeUpdate);
        GIDMonikerSet testGIDMonikerSet = gIDMonikerSetList.get(gIDMonikerSetList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingGIDMonikerSet() throws Exception {
        int databaseSizeBeforeUpdate = gIDMonikerSetRepository.findAll().size();

        // Create the GIDMonikerSet

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGIDMonikerSetMockMvc.perform(put("/api/gid-moniker-sets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gIDMonikerSet)))
            .andExpect(status().isBadRequest());

        // Validate the GIDMonikerSet in the database
        List<GIDMonikerSet> gIDMonikerSetList = gIDMonikerSetRepository.findAll();
        assertThat(gIDMonikerSetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGIDMonikerSet() throws Exception {
        // Initialize the database
        gIDMonikerSetRepository.saveAndFlush(gIDMonikerSet);

        int databaseSizeBeforeDelete = gIDMonikerSetRepository.findAll().size();

        // Delete the gIDMonikerSet
        restGIDMonikerSetMockMvc.perform(delete("/api/gid-moniker-sets/{id}", gIDMonikerSet.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GIDMonikerSet> gIDMonikerSetList = gIDMonikerSetRepository.findAll();
        assertThat(gIDMonikerSetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
