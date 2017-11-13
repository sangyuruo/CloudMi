package com.emcloud.mi.service;

import com.emcloud.mi.domain.MultiwaySwitchInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing MultiwaySwitchInfo.
 */
public interface MultiwaySwitchInfoService {

    /**
     * Save a multiwaySwitchInfo.
     *
     * @param multiwaySwitchInfo the entity to save
     * @return the persisted entity
     */
    MultiwaySwitchInfo save(MultiwaySwitchInfo multiwaySwitchInfo);

    /**
     *  Get all the multiwaySwitchInfos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MultiwaySwitchInfo> findAll(Pageable pageable);

    /**
     *  Get the "id" multiwaySwitchInfo.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MultiwaySwitchInfo findOne(Long id);

    /**
     *  Delete the "id" multiwaySwitchInfo.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
