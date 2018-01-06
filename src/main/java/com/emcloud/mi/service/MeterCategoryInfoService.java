package com.emcloud.mi.service;

import com.emcloud.mi.domain.MeterCategoryInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing MeterCategoryInfo.
 */
public interface MeterCategoryInfoService {

    /**
     * Save a meterCategoryInfo.
     *
     * @param meterCategoryInfo the entity to save
     * @return the persisted entity
     */
    MeterCategoryInfo save(MeterCategoryInfo meterCategoryInfo);

    /**
     * update a meterCategoryInfo.
     *
     * @param meterCategoryInfo the entity to update
     * @return the persisted entity
     */
    MeterCategoryInfo update(MeterCategoryInfo meterCategoryInfo);

    /**
     *  Get all the meterCategoryInfos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MeterCategoryInfo> findAll(Pageable pageable);

    /**
     *  Get the "meterType" meterCategoryInfo.
     *
     *  @param meterType the id of the entity
     *  @return the entity
     */
    Page<MeterCategoryInfo> findByMeterType(Pageable pageable,String meterType);

    /**
     *  Get the "id" meterCategoryInfo.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MeterCategoryInfo findOne(Long id);

    /**
     *  Delete the "id" meterCategoryInfo.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
