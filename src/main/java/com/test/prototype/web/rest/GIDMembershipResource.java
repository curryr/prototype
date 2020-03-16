package com.test.prototype.web.rest;

import com.test.prototype.domain.GIDMembership;
import com.test.prototype.repository.GIDMembershipRepository;
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
 * REST controller for managing {@link com.test.prototype.domain.GIDMembership}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GIDMembershipResource {

    private final Logger log = LoggerFactory.getLogger(GIDMembershipResource.class);

    private static final String ENTITY_NAME = "gIDMembership";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GIDMembershipRepository gIDMembershipRepository;

    public GIDMembershipResource(GIDMembershipRepository gIDMembershipRepository) {
        this.gIDMembershipRepository = gIDMembershipRepository;
    }

    /**
     * {@code POST  /gid-memberships} : Create a new gIDMembership.
     *
     * @param gIDMembership the gIDMembership to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gIDMembership, or with status {@code 400 (Bad Request)} if the gIDMembership has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gid-memberships")
    public ResponseEntity<GIDMembership> createGIDMembership(@RequestBody GIDMembership gIDMembership) throws URISyntaxException {
        log.debug("REST request to save GIDMembership : {}", gIDMembership);
        if (gIDMembership.getId() != null) {
            throw new BadRequestAlertException("A new gIDMembership cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GIDMembership result = gIDMembershipRepository.save(gIDMembership);
        return ResponseEntity.created(new URI("/api/gid-memberships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gid-memberships} : Updates an existing gIDMembership.
     *
     * @param gIDMembership the gIDMembership to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gIDMembership,
     * or with status {@code 400 (Bad Request)} if the gIDMembership is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gIDMembership couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gid-memberships")
    public ResponseEntity<GIDMembership> updateGIDMembership(@RequestBody GIDMembership gIDMembership) throws URISyntaxException {
        log.debug("REST request to update GIDMembership : {}", gIDMembership);
        if (gIDMembership.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GIDMembership result = gIDMembershipRepository.save(gIDMembership);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gIDMembership.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /gid-memberships} : get all the gIDMemberships.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gIDMemberships in body.
     */
    @GetMapping("/gid-memberships")
    public ResponseEntity<List<GIDMembership>> getAllGIDMemberships(Pageable pageable) {
        log.debug("REST request to get a page of GIDMemberships");
        Page<GIDMembership> page = gIDMembershipRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /gid-memberships/:id} : get the "id" gIDMembership.
     *
     * @param id the id of the gIDMembership to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gIDMembership, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gid-memberships/{id}")
    public ResponseEntity<GIDMembership> getGIDMembership(@PathVariable Long id) {
        log.debug("REST request to get GIDMembership : {}", id);
        Optional<GIDMembership> gIDMembership = gIDMembershipRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(gIDMembership);
    }

    /**
     * {@code DELETE  /gid-memberships/:id} : delete the "id" gIDMembership.
     *
     * @param id the id of the gIDMembership to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gid-memberships/{id}")
    public ResponseEntity<Void> deleteGIDMembership(@PathVariable Long id) {
        log.debug("REST request to delete GIDMembership : {}", id);
        gIDMembershipRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
