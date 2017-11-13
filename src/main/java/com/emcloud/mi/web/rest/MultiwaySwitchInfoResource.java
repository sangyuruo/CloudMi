package com.emcloud.mi.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emcloud.mi.domain.MultiwaySwitchInfo;
import com.emcloud.mi.service.MultiwaySwitchInfoService;
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
 * REST controller for managing MultiwaySwitchInfo.
 */
@RestController
@RequestMapping("/api")
public class MultiwaySwitchInfoResource {

    private final Logger log = LoggerFactory.getLogger(MultiwaySwitchInfoResource.class);

    private static final String ENTITY_NAME = "multiwaySwitchInfo";

    private final MultiwaySwitchInfoService multiwaySwitchInfoService;

    public MultiwaySwitchInfoResource(MultiwaySwitchInfoService multiwaySwitchInfoService) {
        this.multiwaySwitchInfoService = multiwaySwitchInfoService;
    }

    /**
     * POST  /multiway-switch-infos : Create a new multiwaySwitchInfo.
     *
     * @param multiwaySwitchInfo the multiwaySwitchInfo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new multiwaySwitchInfo, or with status 400 (Bad Request) if the multiwaySwitchInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/multiway-switch-infos")
    @Timed
    public ResponseEntity<MultiwaySwitchInfo> createMultiwaySwitchInfo(@Valid @RequestBody MultiwaySwitchInfo multiwaySwitchInfo) throws URISyntaxException {
        log.debug("REST request to save MultiwaySwitchInfo : {}", multiwaySwitchInfo);
        if (multiwaySwitchInfo.getId() != null) {
            throw new BadRequestAlertException("A new multiwaySwitchInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MultiwaySwitchInfo result = multiwaySwitchInfoService.save(multiwaySwitchInfo);
        return ResponseEntity.created(new URI("/api/multiway-switch-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /multiway-switch-infos : Updates an existing multiwaySwitchInfo.
     *
     * @param multiwaySwitchInfo the multiwaySwitchInfo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated multiwaySwitchInfo,
     * or with status 400 (Bad Request) if the multiwaySwitchInfo is not valid,
     * or with status 500 (Internal Server Error) if the multiwaySwitchInfo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/multiway-switch-infos")
    @Timed
    public ResponseEntity<MultiwaySwitchInfo> updateMultiwaySwitchInfo(@Valid @RequestBody MultiwaySwitchInfo multiwaySwitchInfo) throws URISyntaxException {
        log.debug("REST request to update MultiwaySwitchInfo : {}", multiwaySwitchInfo);
        if (multiwaySwitchInfo.getId() == null) {
            return createMultiwaySwitchInfo(multiwaySwitchInfo);
        }
        MultiwaySwitchInfo result = multiwaySwitchInfoService.save(multiwaySwitchInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, multiwaySwitchInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /multiway-switch-infos : get all the multiwaySwitchInfos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of multiwaySwitchInfos in body
     */
    @GetMapping("/multiway-switch-infos")
    @Timed
    public ResponseEntity<List<MultiwaySwitchInfo>> getAllMultiwaySwitchInfos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MultiwaySwitchInfos");
        Page<MultiwaySwitchInfo> page = multiwaySwitchInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/multiway-switch-infos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /multiway-switch-infos/:id : get the "id" multiwaySwitchInfo.
     *
     * @param id the id of the multiwaySwitchInfo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the multiwaySwitchInfo, or with status 404 (Not Found)
     */
    @GetMapping("/multiway-switch-infos/{id}")
    @Timed
    public ResponseEntity<MultiwaySwitchInfo> getMultiwaySwitchInfo(@PathVariable Long id) {
        log.debug("REST request to get MultiwaySwitchInfo : {}", id);
        MultiwaySwitchInfo multiwaySwitchInfo = multiwaySwitchInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(multiwaySwitchInfo));
    }

    /**
     * DELETE  /multiway-switch-infos/:id : delete the "id" multiwaySwitchInfo.
     *
     * @param id the id of the multiwaySwitchInfo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/multiway-switch-infos/{id}")
    @Timed
    public ResponseEntity<Void> deleteMultiwaySwitchInfo(@PathVariable Long id) {
        log.debug("REST request to delete MultiwaySwitchInfo : {}", id);
        multiwaySwitchInfoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
