package com.emcloud.mi.service.impl;

import com.emcloud.mi.domain.MeterInfo;
import com.emcloud.mi.security.SecurityUtils;
import com.emcloud.mi.service.MeterStatusService;
import com.emcloud.mi.domain.MeterStatus;
import com.emcloud.mi.repository.MeterStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;


/**
 * Service Implementation for managing MeterStatus.
 */
@Service
@Transactional
public class MeterStatusServiceImpl implements MeterStatusService{

    private final Logger log = LoggerFactory.getLogger(MeterStatusServiceImpl.class);

    private final MeterStatusRepository meterStatusRepository;

    public MeterStatusServiceImpl(MeterStatusRepository meterStatusRepository) {
        this.meterStatusRepository = meterStatusRepository;
    }

    /**
     * Save a meterStatus.
     *
     * @param meterStatus the entity to save
     * @return the persisted entity
     */
    @Override
    public MeterStatus save(MeterStatus meterStatus) {
        log.debug("Request to save MeterStatus : {}", meterStatus);
        meterStatus.setRecordTime(Instant.now());
        return meterStatusRepository.save(meterStatus);
    }

    /**
     * update a meterStatus.
     *
     * @param meterStatus the entity to update
     * @return the persisted entity
     */
    @Override
    public MeterStatus update(MeterStatus meterStatus) {
        log.debug("Request to save MeterStatus : {}", meterStatus);
        meterStatus.setRecordTime(Instant.now());
        return meterStatusRepository.save(meterStatus);
    }
    /**
     *  Get all the meterStatuses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MeterStatus> findAll(Pageable pageable) {
        log.debug("Request to get all MeterStatuses");
        return meterStatusRepository.findAll(pageable);
    }

    /**
     *  Get one meterStatus by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MeterStatus findOne(Long id) {
        log.debug("Request to get MeterStatus : {}", id);
        return meterStatusRepository.findOne(id);
    }

    /**
     *  Delete the  meterStatus by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MeterStatus : {}", id);
        meterStatusRepository.delete(id);
    }
}
