package com.test.prototype.web.rest;

import com.test.prototype.domain.GIDIdentity;
import com.test.prototype.repository.GIDIdentityRepository;
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
 * REST controller for managing {@link com.test.prototype.domain.GIDIdentity}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GIDIdentityResource {

    private final Logger log = LoggerFactory.getLogger(GIDIdentityResource.class);

    private static final String ENTITY_NAME = "gIDIdentity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GIDIdentityRepository gIDIdentityRepository;

    public GIDIdentityResource(GIDIdentityRepository gIDIdentityRepository) {
        this.gIDIdentityRepository = gIDIdentityRepository;
    }

    /**
     * {@code POST  /gid-identities} : Create a new gIDIdentity.
     *
     * @param gIDIdentity the gIDIdentity to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gIDIdentity, or with status {@code 400 (Bad Request)} if the gIDIdentity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gid-identities")
    public ResponseEntity<GIDIdentity> createGIDIdentity(@RequestBody GIDIdentity gIDIdentity) throws URISyntaxException {
        log.debug("REST request to save GIDIdentity : {}", gIDIdentity);
        if (gIDIdentity.getId() != null) {
            throw new BadRequestAlertException("A new gIDIdentity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GIDIdentity result = gIDIdentityRepository.save(gIDIdentity);
        return ResponseEntity.created(new URI("/api/gid-identities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gid-identities} : Updates an existing gIDIdentity.
     *
     * @param gIDIdentity the gIDIdentity to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gIDIdentity,
     * or with status {@code 400 (Bad Request)} if the gIDIdentity is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gIDIdentity couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gid-identities")
    public ResponseEntity<GIDIdentity> updateGIDIdentity(@RequestBody GIDIdentity gIDIdentity) throws URISyntaxException {
        log.debug("REST request to update GIDIdentity : {}", gIDIdentity);
        if (gIDIdentity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GIDIdentity result = gIDIdentityRepository.save(gIDIdentity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gIDIdentity.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /gid-identities} : get all the gIDIdentities.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gIDIdentities in body.
     */
    @GetMapping("/gid-identities")
    public ResponseEntity<List<GIDIdentity>> getAllGIDIdentities(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of GIDIdentities");
        Page<GIDIdentity> page;
        if (eagerload) {
            page = gIDIdentityRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = gIDIdentityRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /gid-identities/:id} : get the "id" gIDIdentity.
     *
     * @param id the id of the gIDIdentity to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gIDIdentity, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gid-identities/{id}")
    public ResponseEntity<GIDIdentity> getGIDIdentity(@PathVariable Long id) {
        log.debug("REST request to get GIDIdentity : {}", id);
        Optional<GIDIdentity> gIDIdentity = gIDIdentityRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(gIDIdentity);
    }

    /**
     * {@code DELETE  /gid-identities/:id} : delete the "id" gIDIdentity.
     *
     * @param id the id of the gIDIdentity to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gid-identities/{id}")
    public ResponseEntity<Void> deleteGIDIdentity(@PathVariable Long id) {
        log.debug("REST request to delete GIDIdentity : {}", id);
        gIDIdentityRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
