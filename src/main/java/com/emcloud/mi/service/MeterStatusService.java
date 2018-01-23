package com.emcloud.mi.service;

import com.emcloud.mi.domain.MeterStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing MeterStatus.
 */
public interface MeterStatusService {

    /**
     * Save a meterStatus.
     *
     * @param meterStatus the entity to save
     * @return the persisted entity
     */
    MeterStatus save(MeterStatus meterStatus);

    /**
     *  Get all the meterStatuses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MeterStatus> findAll(Pageable pageable);

    /**
     *  Get the "id" meterStatus.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MeterStatus findOne(Long id);

    /**
     *  Delete the "id" meterStatus.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
