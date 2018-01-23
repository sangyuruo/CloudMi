package com.emcloud.mi.service.impl;

import com.emcloud.mi.service.MultiwaySwitchService;
import com.emcloud.mi.domain.MultiwaySwitch;
import com.emcloud.mi.repository.MultiwaySwitchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing MultiwaySwitch.
 */
@Service
@Transactional
public class MultiwaySwitchServiceImpl implements MultiwaySwitchService{

    private final Logger log = LoggerFactory.getLogger(MultiwaySwitchServiceImpl.class);

    private final MultiwaySwitchRepository multiwaySwitchRepository;

    public MultiwaySwitchServiceImpl(MultiwaySwitchRepository multiwaySwitchRepository) {
        this.multiwaySwitchRepository = multiwaySwitchRepository;
    }

    /**
     * Save a multiwaySwitch.
     *
     * @param multiwaySwitch the entity to save
     * @return the persisted entity
     */
    @Override
    public MultiwaySwitch save(MultiwaySwitch multiwaySwitch) {
        log.debug("Request to save MultiwaySwitch : {}", multiwaySwitch);
        return multiwaySwitchRepository.save(multiwaySwitch);
    }

    /**
     *  Get all the multiwaySwitches.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MultiwaySwitch> findAll(Pageable pageable) {
        log.debug("Request to get all MultiwaySwitches");
        return multiwaySwitchRepository.findAll(pageable);
    }

    /**
     *  Get one multiwaySwitch by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MultiwaySwitch findOne(Long id) {
        log.debug("Request to get MultiwaySwitch : {}", id);
        return multiwaySwitchRepository.findOne(id);
    }

    /**
     *  Delete the  multiwaySwitch by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MultiwaySwitch : {}", id);
        multiwaySwitchRepository.delete(id);
    }
}
