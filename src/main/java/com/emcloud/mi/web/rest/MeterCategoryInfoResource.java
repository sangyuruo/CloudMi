package com.emcloud.mi.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emcloud.mi.domain.MeterCategoryInfo;
import com.emcloud.mi.service.MeterCategoryInfoService;
import com.emcloud.mi.web.rest.errors.BadRequestAlertException;
import com.emcloud.mi.web.rest.util.HeaderUtil;
import com.emcloud.mi.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.apache.commons.lang.StringUtils;
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
 * REST controller for managing MeterCategoryInfo.
 */
@RestController
@RequestMapping("/api")
public class MeterCategoryInfoResource {

    private final Logger log = LoggerFactory.getLogger(MeterCategoryInfoResource.class);

    private static final String ENTITY_NAME = "meterCategoryInfo";

    private final MeterCategoryInfoService meterCategoryInfoService;

    public MeterCategoryInfoResource(MeterCategoryInfoService meterCategoryInfoService) {
        this.meterCategoryInfoService = meterCategoryInfoService;
    }

    /**
     * POST  /meter-category-infos : Create a new meterCategoryInfo.
     *
     * @param meterCategoryInfo the meterCategoryInfo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new meterCategoryInfo, or with status 400 (Bad Request) if the meterCategoryInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/meter-category-infos")
    @Timed
    public ResponseEntity<MeterCategoryInfo> createMeterCategoryInfo(@Valid @RequestBody MeterCategoryInfo meterCategoryInfo) throws URISyntaxException {
        log.debug("REST request to save MeterCategoryInfo : {}", meterCategoryInfo);
        if (meterCategoryInfo.getId() != null) {
            throw new BadRequestAlertException("A new meterCategoryInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MeterCategoryInfo result = meterCategoryInfoService.save(meterCategoryInfo);
        return ResponseEntity.created(new URI("/api/meter-category-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /meter-category-infos : Updates an existing meterCategoryInfo.
     *
     * @param meterCategoryInfo the meterCategoryInfo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated meterCategoryInfo,
     * or with status 400 (Bad Request) if the meterCategoryInfo is not valid,
     * or with status 500 (Internal Server Error) if the meterCategoryInfo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/meter-category-infos")
    @Timed
    public ResponseEntity<MeterCategoryInfo> updateMeterCategoryInfo(@Valid @RequestBody MeterCategoryInfo meterCategoryInfo) throws URISyntaxException {
        log.debug("REST request to update MeterCategoryInfo : {}", meterCategoryInfo);
        if (meterCategoryInfo.getId() == null) {
            return createMeterCategoryInfo(meterCategoryInfo);
        }
        MeterCategoryInfo result = meterCategoryInfoService.update(meterCategoryInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, meterCategoryInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /meter-category-infos : get all the meterCategoryInfos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of meterCategoryInfos in body
     */
    @GetMapping("/meter-category-infos")
    @Timed
    public ResponseEntity<List<MeterCategoryInfo>> getAllMeterCategoryInfosByMeterName
    (@RequestParam(value = "query",required = false) String meterName , @ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MeterCategoryInfos");
        Page<MeterCategoryInfo> page;
        if(StringUtils.isBlank(meterName)){
            page = meterCategoryInfoService.findAll(pageable);
        }else{
            page = meterCategoryInfoService.findByMeterName(pageable,meterName);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/meter-category-infos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /meter-category-infos/:id : get the "id" meterCategoryInfo.
     *
     * @param id the id of the meterCategoryInfo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the meterCategoryInfo, or with status 404 (Not Found)
     */
    @GetMapping("/meter-category-infos/{id}")
    @Timed
    public ResponseEntity<MeterCategoryInfo> getMeterCategoryInfo(@PathVariable Long id) {
        log.debug("REST request to get MeterCategoryInfo : {}", id);
        MeterCategoryInfo meterCategoryInfo = meterCategoryInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(meterCategoryInfo));
    }

    /**
     * DELETE  /meter-category-infos/:id : delete the "id" meterCategoryInfo.
     *
     * @param id the id of the meterCategoryInfo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/meter-category-infos/{id}")
    @Timed
    public ResponseEntity<Void> deleteMeterCategoryInfo(@PathVariable Long id) {
        log.debug("REST request to delete MeterCategoryInfo : {}", id);
        meterCategoryInfoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
