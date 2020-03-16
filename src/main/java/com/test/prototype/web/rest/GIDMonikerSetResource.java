package com.test.prototype.web.rest;

import com.test.prototype.domain.GIDMonikerSet;
import com.test.prototype.repository.GIDMonikerSetRepository;
import com.test.prototype.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.test.prototype.domain.GIDMonikerSet}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GIDMonikerSetResource {

    private final Logger log = LoggerFactory.getLogger(GIDMonikerSetResource.class);

    private static final String ENTITY_NAME = "gIDMonikerSet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GIDMonikerSetRepository gIDMonikerSetRepository;

    public GIDMonikerSetResource(GIDMonikerSetRepository gIDMonikerSetRepository) {
        this.gIDMonikerSetRepository = gIDMonikerSetRepository;
    }

    /**
     * {@code POST  /gid-moniker-sets} : Create a new gIDMonikerSet.
     *
     * @param gIDMonikerSet the gIDMonikerSet to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gIDMonikerSet, or with status {@code 400 (Bad Request)} if the gIDMonikerSet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gid-moniker-sets")
    public ResponseEntity<GIDMonikerSet> createGIDMonikerSet(@RequestBody GIDMonikerSet gIDMonikerSet) throws URISyntaxException {
        log.debug("REST request to save GIDMonikerSet : {}", gIDMonikerSet);
        if (gIDMonikerSet.getId() != null) {
            throw new BadRequestAlertException("A new gIDMonikerSet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GIDMonikerSet result = gIDMonikerSetRepository.save(gIDMonikerSet);
        return ResponseEntity.created(new URI("/api/gid-moniker-sets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gid-moniker-sets} : Updates an existing gIDMonikerSet.
     *
     * @param gIDMonikerSet the gIDMonikerSet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gIDMonikerSet,
     * or with status {@code 400 (Bad Request)} if the gIDMonikerSet is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gIDMonikerSet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gid-moniker-sets")
    public ResponseEntity<GIDMonikerSet> updateGIDMonikerSet(@RequestBody GIDMonikerSet gIDMonikerSet) throws URISyntaxException {
        log.debug("REST request to update GIDMonikerSet : {}", gIDMonikerSet);
        if (gIDMonikerSet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GIDMonikerSet result = gIDMonikerSetRepository.save(gIDMonikerSet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gIDMonikerSet.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /gid-moniker-sets} : get all the gIDMonikerSets.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gIDMonikerSets in body.
     */
    @GetMapping("/gid-moniker-sets")
    public List<GIDMonikerSet> getAllGIDMonikerSets(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all GIDMonikerSets");
        return gIDMonikerSetRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /gid-moniker-sets/:id} : get the "id" gIDMonikerSet.
     *
     * @param id the id of the gIDMonikerSet to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gIDMonikerSet, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gid-moniker-sets/{id}")
    public ResponseEntity<GIDMonikerSet> getGIDMonikerSet(@PathVariable Long id) {
        log.debug("REST request to get GIDMonikerSet : {}", id);
        Optional<GIDMonikerSet> gIDMonikerSet = gIDMonikerSetRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(gIDMonikerSet);
    }

    /**
     * {@code DELETE  /gid-moniker-sets/:id} : delete the "id" gIDMonikerSet.
     *
     * @param id the id of the gIDMonikerSet to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gid-moniker-sets/{id}")
    public ResponseEntity<Void> deleteGIDMonikerSet(@PathVariable Long id) {
        log.debug("REST request to delete GIDMonikerSet : {}", id);
        gIDMonikerSetRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
