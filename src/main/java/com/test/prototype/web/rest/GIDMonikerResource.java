package com.test.prototype.web.rest;

import com.test.prototype.domain.GIDMoniker;
import com.test.prototype.repository.GIDMonikerRepository;
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
 * REST controller for managing {@link com.test.prototype.domain.GIDMoniker}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GIDMonikerResource {

    private final Logger log = LoggerFactory.getLogger(GIDMonikerResource.class);

    private static final String ENTITY_NAME = "gIDMoniker";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GIDMonikerRepository gIDMonikerRepository;

    public GIDMonikerResource(GIDMonikerRepository gIDMonikerRepository) {
        this.gIDMonikerRepository = gIDMonikerRepository;
    }

    /**
     * {@code POST  /gid-monikers} : Create a new gIDMoniker.
     *
     * @param gIDMoniker the gIDMoniker to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gIDMoniker, or with status {@code 400 (Bad Request)} if the gIDMoniker has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gid-monikers")
    public ResponseEntity<GIDMoniker> createGIDMoniker(@RequestBody GIDMoniker gIDMoniker) throws URISyntaxException {
        log.debug("REST request to save GIDMoniker : {}", gIDMoniker);
        if (gIDMoniker.getId() != null) {
            throw new BadRequestAlertException("A new gIDMoniker cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GIDMoniker result = gIDMonikerRepository.save(gIDMoniker);
        return ResponseEntity.created(new URI("/api/gid-monikers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gid-monikers} : Updates an existing gIDMoniker.
     *
     * @param gIDMoniker the gIDMoniker to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gIDMoniker,
     * or with status {@code 400 (Bad Request)} if the gIDMoniker is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gIDMoniker couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gid-monikers")
    public ResponseEntity<GIDMoniker> updateGIDMoniker(@RequestBody GIDMoniker gIDMoniker) throws URISyntaxException {
        log.debug("REST request to update GIDMoniker : {}", gIDMoniker);
        if (gIDMoniker.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GIDMoniker result = gIDMonikerRepository.save(gIDMoniker);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gIDMoniker.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /gid-monikers} : get all the gIDMonikers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gIDMonikers in body.
     */
    @GetMapping("/gid-monikers")
    public ResponseEntity<List<GIDMoniker>> getAllGIDMonikers(Pageable pageable) {
        log.debug("REST request to get a page of GIDMonikers");
        Page<GIDMoniker> page = gIDMonikerRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /gid-monikers/:id} : get the "id" gIDMoniker.
     *
     * @param id the id of the gIDMoniker to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gIDMoniker, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gid-monikers/{id}")
    public ResponseEntity<GIDMoniker> getGIDMoniker(@PathVariable Long id) {
        log.debug("REST request to get GIDMoniker : {}", id);
        Optional<GIDMoniker> gIDMoniker = gIDMonikerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(gIDMoniker);
    }

    /**
     * {@code DELETE  /gid-monikers/:id} : delete the "id" gIDMoniker.
     *
     * @param id the id of the gIDMoniker to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gid-monikers/{id}")
    public ResponseEntity<Void> deleteGIDMoniker(@PathVariable Long id) {
        log.debug("REST request to delete GIDMoniker : {}", id);
        gIDMonikerRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
