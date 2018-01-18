package com.emcloud.mi.service;

import com.emcloud.mi.domain.MeterInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing MeterInfo.
 */
public interface MeterInfoService {

    /**
     * Save a meterInfo.
     *
     * @param meterInfo the entity to save
     * @return the persisted entity
     */
    MeterInfo save(MeterInfo meterInfo);

    /**
     * update a meterInfo.
     *
     * @param meterInfo the entity to update
     * @return the persisted entity
     */
    MeterInfo update(MeterInfo meterInfo);

    /**
     *  Get all the meterInfos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MeterInfo> findAll(Pageable pageable);

    /**
     *  Get all the meterInfos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MeterInfo> findAllByCompanyCodeAndOrganizationCode(String companyCode, String orgCode , Pageable pageable);


    /**
     *  Get all the meterInfos.
     *
     *  @param comPointCode the pagination information
     *  @return the list of entities
     */
    List<MeterInfo> findAllByComPointCode(String comPointCode);

    /**
     *  Get all the meterInfos.
     *
     *  @param meterCode the pagination information
     *  @return the list of entities
     */
    MeterInfo findOneByMeterCode(String meterCode);

 /*   *//**
     *  Get the "id" meterInfo.
     *
     *  @param meterCode,comPointCode,registerCode the id of the entity
     *  @return the entity
     *//*
    MeterInfo findOneMeterInfo(String meterCode, String comPointCode, Integer registerCode);
*/
    /**
     *  Get the "id" meterInfo.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MeterInfo findOne(Long id);

    /**
     *  Delete the "id" meterInfo.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
