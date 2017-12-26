package com.emcloud.mi.service;

import com.emcloud.mi.domain.MeterStatus;
import com.emcloud.mi.domain.MultiwaySwitch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing MultiwaySwitch.
 */
public interface MultiwaySwitchService {

    /**
     * Save a multiwaySwitch.
     *
     * @param multiwaySwitch the entity to save
     * @return the persisted entity
     */
    MultiwaySwitch save(MultiwaySwitch multiwaySwitch);

    /**
     * update a multiwaySwitch.
     *
     * @param multiwaySwitch the entity to update
     * @return the persisted entity
     */
    MultiwaySwitch update(MultiwaySwitch multiwaySwitch);

    /**
     *  Get all the multiwaySwitches.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MultiwaySwitch> findAll(Pageable pageable);

    /**
     *  Get the "id" multiwaySwitch.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MultiwaySwitch findOne(Long id);

    /**
     *  Delete the "id" multiwaySwitch.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
