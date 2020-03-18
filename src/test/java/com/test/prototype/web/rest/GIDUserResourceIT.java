package com.test.prototype.web.rest;

import com.test.prototype.PrototypeApp;
import com.test.prototype.domain.GIDUser;
import com.test.prototype.repository.GIDUserRepository;

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
 * Integration tests for the {@link GIDUserResource} REST controller.
 */
@SpringBootTest(classes = PrototypeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class GIDUserResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    @Autowired
    private GIDUserRepository gIDUserRepository;

    @Mock
    private GIDUserRepository gIDUserRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGIDUserMockMvc;

    private GIDUser gIDUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GIDUser createEntity(EntityManager em) {
        GIDUser gIDUser = new GIDUser()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME);
        return gIDUser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GIDUser createUpdatedEntity(EntityManager em) {
        GIDUser gIDUser = new GIDUser()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME);
        return gIDUser;
    }

    @BeforeEach
    public void initTest() {
        gIDUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createGIDUser() throws Exception {
        int databaseSizeBeforeCreate = gIDUserRepository.findAll().size();

        // Create the GIDUser
        restGIDUserMockMvc.perform(post("/api/gid-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gIDUser)))
            .andExpect(status().isCreated());

        // Validate the GIDUser in the database
        List<GIDUser> gIDUserList = gIDUserRepository.findAll();
        assertThat(gIDUserList).hasSize(databaseSizeBeforeCreate + 1);
        GIDUser testGIDUser = gIDUserList.get(gIDUserList.size() - 1);
        assertThat(testGIDUser.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testGIDUser.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
    }

    @Test
    @Transactional
    public void createGIDUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gIDUserRepository.findAll().size();

        // Create the GIDUser with an existing ID
        gIDUser.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGIDUserMockMvc.perform(post("/api/gid-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gIDUser)))
            .andExpect(status().isBadRequest());

        // Validate the GIDUser in the database
        List<GIDUser> gIDUserList = gIDUserRepository.findAll();
        assertThat(gIDUserList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGIDUsers() throws Exception {
        // Initialize the database
        gIDUserRepository.saveAndFlush(gIDUser);

        // Get all the gIDUserList
        restGIDUserMockMvc.perform(get("/api/gid-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gIDUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllGIDUsersWithEagerRelationshipsIsEnabled() throws Exception {
        GIDUserResource gIDUserResource = new GIDUserResource(gIDUserRepositoryMock);
        when(gIDUserRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGIDUserMockMvc.perform(get("/api/gid-users?eagerload=true"))
            .andExpect(status().isOk());

        verify(gIDUserRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllGIDUsersWithEagerRelationshipsIsNotEnabled() throws Exception {
        GIDUserResource gIDUserResource = new GIDUserResource(gIDUserRepositoryMock);
        when(gIDUserRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGIDUserMockMvc.perform(get("/api/gid-users?eagerload=true"))
            .andExpect(status().isOk());

        verify(gIDUserRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getGIDUser() throws Exception {
        // Initialize the database
        gIDUserRepository.saveAndFlush(gIDUser);

        // Get the gIDUser
        restGIDUserMockMvc.perform(get("/api/gid-users/{id}", gIDUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gIDUser.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingGIDUser() throws Exception {
        // Get the gIDUser
        restGIDUserMockMvc.perform(get("/api/gid-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGIDUser() throws Exception {
        // Initialize the database
        gIDUserRepository.saveAndFlush(gIDUser);

        int databaseSizeBeforeUpdate = gIDUserRepository.findAll().size();

        // Update the gIDUser
        GIDUser updatedGIDUser = gIDUserRepository.findById(gIDUser.getId()).get();
        // Disconnect from session so that the updates on updatedGIDUser are not directly saved in db
        em.detach(updatedGIDUser);
        updatedGIDUser
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME);

        restGIDUserMockMvc.perform(put("/api/gid-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGIDUser)))
            .andExpect(status().isOk());

        // Validate the GIDUser in the database
        List<GIDUser> gIDUserList = gIDUserRepository.findAll();
        assertThat(gIDUserList).hasSize(databaseSizeBeforeUpdate);
        GIDUser testGIDUser = gIDUserList.get(gIDUserList.size() - 1);
        assertThat(testGIDUser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testGIDUser.getLastName()).isEqualTo(UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingGIDUser() throws Exception {
        int databaseSizeBeforeUpdate = gIDUserRepository.findAll().size();

        // Create the GIDUser

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGIDUserMockMvc.perform(put("/api/gid-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gIDUser)))
            .andExpect(status().isBadRequest());

        // Validate the GIDUser in the database
        List<GIDUser> gIDUserList = gIDUserRepository.findAll();
        assertThat(gIDUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGIDUser() throws Exception {
        // Initialize the database
        gIDUserRepository.saveAndFlush(gIDUser);

        int databaseSizeBeforeDelete = gIDUserRepository.findAll().size();

        // Delete the gIDUser
        restGIDUserMockMvc.perform(delete("/api/gid-users/{id}", gIDUser.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GIDUser> gIDUserList = gIDUserRepository.findAll();
        assertThat(gIDUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
