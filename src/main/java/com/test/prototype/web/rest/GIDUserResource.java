package com.test.prototype.web.rest;

import com.test.prototype.domain.GIDUser;
import com.test.prototype.repository.GIDUserRepository;
import com.test.prototype.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.test.prototype.domain.GIDUser}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GIDUserResource {

    private final Logger log = LoggerFactory.getLogger(GIDUserResource.class);

    private static final String ENTITY_NAME = "gIDUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GIDUserRepository gIDUserRepository;

    public GIDUserResource(GIDUserRepository gIDUserRepository) {
        this.gIDUserRepository = gIDUserRepository;
    }

    /**
     * {@code POST  /gid-users} : Create a new gIDUser.
     *
     * @param gIDUser the gIDUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gIDUser, or with status {@code 400 (Bad Request)} if the gIDUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gid-users")
    public ResponseEntity<GIDUser> createGIDUser(@RequestBody GIDUser gIDUser) throws URISyntaxException {
        log.debug("REST request to save GIDUser : {}", gIDUser);
        if (gIDUser.getId() != null) {
            throw new BadRequestAlertException("A new gIDUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GIDUser result = gIDUserRepository.save(gIDUser);
        return ResponseEntity.created(new URI("/api/gid-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gid-users} : Updates an existing gIDUser.
     *
     * @param gIDUser the gIDUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gIDUser,
     * or with status {@code 400 (Bad Request)} if the gIDUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gIDUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gid-users")
    public ResponseEntity<GIDUser> updateGIDUser(@RequestBody GIDUser gIDUser) throws URISyntaxException {
        log.debug("REST request to update GIDUser : {}", gIDUser);
        if (gIDUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GIDUser result = gIDUserRepository.save(gIDUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gIDUser.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /gid-users} : get all the gIDUsers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gIDUsers in body.
     */
    @GetMapping("/gid-users")
    public ResponseEntity<List<GIDUser>> getAllGIDUsers(Pageable pageable) {
        log.debug("REST request to get a page of GIDUsers");
        Page<GIDUser> page = gIDUserRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /gid-users/:id} : get the "id" gIDUser.
     *
     * @param id the id of the gIDUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gIDUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gid-users/{id}")
    public ResponseEntity<GIDUser> getGIDUser(@PathVariable Long id) {
        log.debug("REST request to get GIDUser : {}", id);
        Optional<GIDUser> gIDUser = gIDUserRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(gIDUser);
    }

    /**
     * {@code DELETE  /gid-users/:id} : delete the "id" gIDUser.
     *
     * @param id the id of the gIDUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gid-users/{id}")
    public ResponseEntity<Void> deleteGIDUser(@PathVariable Long id) {
        log.debug("REST request to delete GIDUser : {}", id);
        gIDUserRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
