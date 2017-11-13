package com.emcloud.mi.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emcloud.mi.domain.MultiwaySwitch;
import com.emcloud.mi.service.MultiwaySwitchService;
import com.emcloud.mi.web.rest.errors.BadRequestAlertException;
import com.emcloud.mi.web.rest.util.HeaderUtil;
import com.emcloud.mi.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MultiwaySwitch.
 */
@RestController
@RequestMapping("/api")
public class MultiwaySwitchResource {

    private final Logger log = LoggerFactory.getLogger(MultiwaySwitchResource.class);

    private static final String ENTITY_NAME = "multiwaySwitch";

    private final MultiwaySwitchService multiwaySwitchService;

    public MultiwaySwitchResource(MultiwaySwitchService multiwaySwitchService) {
        this.multiwaySwitchService = multiwaySwitchService;
    }

    /**
     * POST  /multiway-switches : Create a new multiwaySwitch.
     *
     * @param multiwaySwitch the multiwaySwitch to create
     * @return the ResponseEntity with status 201 (Created) and with body the new multiwaySwitch, or with status 400 (Bad Request) if the multiwaySwitch has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/multiway-switches")
    @Timed
    public ResponseEntity<MultiwaySwitch> createMultiwaySwitch(@Valid @RequestBody MultiwaySwitch multiwaySwitch) throws URISyntaxException {
        log.debug("REST request to save MultiwaySwitch : {}", multiwaySwitch);
        if (multiwaySwitch.getId() != null) {
            throw new BadRequestAlertException("A new multiwaySwitch cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MultiwaySwitch result = multiwaySwitchService.save(multiwaySwitch);
        return ResponseEntity.created(new URI("/api/multiway-switches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /multiway-switches : Updates an existing multiwaySwitch.
     *
     * @param multiwaySwitch the multiwaySwitch to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated multiwaySwitch,
     * or with status 400 (Bad Request) if the multiwaySwitch is not valid,
     * or with status 500 (Internal Server Error) if the multiwaySwitch couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/multiway-switches")
    @Timed
    public ResponseEntity<MultiwaySwitch> updateMultiwaySwitch(@Valid @RequestBody MultiwaySwitch multiwaySwitch) throws URISyntaxException {
        log.debug("REST request to update MultiwaySwitch : {}", multiwaySwitch);
        if (multiwaySwitch.getId() == null) {
            return createMultiwaySwitch(multiwaySwitch);
        }
        MultiwaySwitch result = multiwaySwitchService.save(multiwaySwitch);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, multiwaySwitch.getId().toString()))
            .body(result);
    }

    /**
     * GET  /multiway-switches : get all the multiwaySwitches.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of multiwaySwitches in body
     */
    @GetMapping("/multiway-switches")
    @Timed
    public ResponseEntity<List<MultiwaySwitch>> getAllMultiwaySwitches(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MultiwaySwitches");
        Page<MultiwaySwitch> page = multiwaySwitchService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/multiway-switches");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /multiway-switches/:id : get the "id" multiwaySwitch.
     *
     * @param id the id of the multiwaySwitch to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the multiwaySwitch, or with status 404 (Not Found)
     */
    @GetMapping("/multiway-switches/{id}")
    @Timed
    public ResponseEntity<MultiwaySwitch> getMultiwaySwitch(@PathVariable Long id) {
        log.debug("REST request to get MultiwaySwitch : {}", id);
        MultiwaySwitch multiwaySwitch = multiwaySwitchService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(multiwaySwitch));
    }

    /**
     * DELETE  /multiway-switches/:id : delete the "id" multiwaySwitch.
     *
     * @param id the id of the multiwaySwitch to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/multiway-switches/{id}")
    @Timed
    public ResponseEntity<Void> deleteMultiwaySwitch(@PathVariable Long id) {
        log.debug("REST request to delete MultiwaySwitch : {}", id);
        multiwaySwitchService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
