package com.emcloud.mi.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emcloud.mi.domain.MeterInfo;
import com.emcloud.mi.service.MeterInfoService;
import com.emcloud.mi.web.rest.errors.BadRequestAlertException;
import com.emcloud.mi.web.rest.util.HeaderUtil;
import com.emcloud.mi.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
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
 * REST controller for managing MeterInfo.
 */
@RestController
@RequestMapping("/api")
public class MeterInfoResource {

    private final Logger log = LoggerFactory.getLogger(MeterInfoResource.class);

    private static final String ENTITY_NAME = "meterInfo";

    private final MeterInfoService meterInfoService;

    public MeterInfoResource(MeterInfoService meterInfoService) {
        this.meterInfoService = meterInfoService;
    }

    /**
     * POST  /meter-infos : Create a new meterInfo.
     *
     * @param meterInfo the meterInfo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new meterInfo, or with status 400 (Bad Request) if the meterInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/meter-infos")
    @Timed
    public ResponseEntity<MeterInfo> createMeterInfo(@Valid @RequestBody MeterInfo meterInfo) throws URISyntaxException {
        log.debug("REST request to save MeterInfo : {}", meterInfo);
        if (meterInfo.getId() != null) {
            throw new BadRequestAlertException("A new meterInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MeterInfo result = meterInfoService.save(meterInfo);
        return ResponseEntity.created(new URI("/api/meter-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /meter-infos : Updates an existing meterInfo.
     *
     * @param meterInfo the meterInfo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated meterInfo,
     * or with status 400 (Bad Request) if the meterInfo is not valid,
     * or with status 500 (Internal Server Error) if the meterInfo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/meter-infos")
    @Timed
    public ResponseEntity<MeterInfo> updateMeterInfo(@Valid @RequestBody MeterInfo meterInfo) throws URISyntaxException {
        log.debug("REST request to update MeterInfo : {}", meterInfo);
        if (meterInfo.getId() == null) {
            return createMeterInfo(meterInfo);
        }
        MeterInfo result = meterInfoService.save(meterInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, meterInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /meter-infos : get all the meterInfos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of meterInfos in body
     */
    @GetMapping("/meter-infos")
    @Timed
    public ResponseEntity<List<MeterInfo>> getAllMeterInfos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MeterInfos");
        Page<MeterInfo> page = meterInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/meter-infos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /meter-infos : get all the meterInfos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of meterInfos in body
     */
    @GetMapping("/meter-infos/byou/{companyCode}/{orgCode}")
    @Timed
    public ResponseEntity<List<MeterInfo>> getAllMeterInfosByCompanyCodeAndOrgCode(@PathVariable String companyCode, @PathVariable String orgCode, @ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MeterInfos by ou");
        Page<MeterInfo> page = meterInfoService.findAllByCompanyCodeAndOrganizationCode(companyCode, orgCode, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/meter-infos/byou/" + companyCode + "/" + orgCode );
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /meter-infos : get all the meterInfos.
     *
     * @param comPointCode the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of meterInfos in body
     */
    @GetMapping("/meter-infos/by-com-point-code")
    @Timed
    public List<MeterInfo> getAllMeterInfosByComPointCode
    (@RequestParam(value = "comPointCode", required = false) String comPointCode) {
        log.debug("REST comPointCode to get a page of MeterInfo");
        List<MeterInfo> list = meterInfoService.findAllByComPointCode(comPointCode);
        return list;
    }

    /**
     * GET  /meter-infos : get all the meterInfos.
     *
     * @param meterCode the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of meterInfos in body
     */
    @GetMapping("/meter-infos/by-meter-code")
    @Timed
    public ResponseEntity<MeterInfo> getMeterInfoByMeterCode
        (@RequestParam(value = "meterCode") String meterCode){
        log.debug("Request to get MeterInfo : {}", meterCode);
        MeterInfo meterInfo = meterInfoService.findOneByMeterCode(meterCode);
        return  ResponseUtil.wrapOrNotFound(Optional.ofNullable(meterInfo));
    }

    /**
     * GET  /meter-infos : get all the meterInfos.
     *
     * @param meterCode the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of meterInfos in body
     *//*
    @GetMapping("/meter-infos/by-meter-code")
    @Timed
    public ResponseEntity<MeterInfo> getOneMeterInfo
    (@RequestParam(value = "meterCode") String meterCode, String comPointCode, Integer registerCode){
        log.debug("Request to get MeterInfo : {}", meterCode,comPointCode,registerCode);
        MeterInfo meterInfo =meterInfoService.findOneMeterInfo(meterCode,comPointCode,registerCode);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(meterCode,comPointCode,registerCode));
    }*/

    /**
     * GET  /meter-infos/:id : get the "id" meterInfo.
     *
     * @param id the id of the meterInfo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the meterInfo, or with status 404 (Not Found)
     */
    @GetMapping("/meter-infos/{id}")
    @Timed
    public ResponseEntity<MeterInfo> getMeterInfo(@PathVariable Long id) {
        log.debug("REST request to get MeterInfo : {}", id);
        MeterInfo meterInfo = meterInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(meterInfo));
    }

    /**
     * DELETE  /meter-infos/:id : delete the "id" meterInfo.
     *
     * @param id the id of the meterInfo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/meter-infos/{id}")
    @Timed
    public ResponseEntity<Void> deleteMeterInfo(@PathVariable Long id) {
        log.debug("REST request to delete MeterInfo : {}", id);
        meterInfoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
