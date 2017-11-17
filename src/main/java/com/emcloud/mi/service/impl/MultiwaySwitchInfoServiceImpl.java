package com.emcloud.mi.service.impl;

import com.emcloud.mi.security.SecurityUtils;
import com.emcloud.mi.service.MultiwaySwitchInfoService;
import com.emcloud.mi.domain.MultiwaySwitchInfo;
import com.emcloud.mi.repository.MultiwaySwitchInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZonedDateTime;


/**
 * Service Implementation for managing MultiwaySwitchInfo.
 */
@Service
@Transactional
public class MultiwaySwitchInfoServiceImpl implements MultiwaySwitchInfoService{

    private final Logger log = LoggerFactory.getLogger(MultiwaySwitchInfoServiceImpl.class);

    private final MultiwaySwitchInfoRepository multiwaySwitchInfoRepository;

    public MultiwaySwitchInfoServiceImpl(MultiwaySwitchInfoRepository multiwaySwitchInfoRepository) {
        this.multiwaySwitchInfoRepository = multiwaySwitchInfoRepository;
    }

    /**
     * Save a multiwaySwitchInfo.
     *
     * @param multiwaySwitchInfo the entity to save
     * @return the persisted entity
     */
    @Override
    public MultiwaySwitchInfo save(MultiwaySwitchInfo multiwaySwitchInfo) {
        log.debug("Request to save MultiwaySwitchInfo : {}", multiwaySwitchInfo);
        return multiwaySwitchInfoRepository.save(multiwaySwitchInfo);
    }

    /**
     * update a multiwaySwitchInfo.
     *
     * @param multiwaySwitchInfo the entity to update
     * @return the persisted entity
     */
    @Override
    public MultiwaySwitchInfo update(MultiwaySwitchInfo multiwaySwitchInfo) {
        log.debug("Request to save Company : {}", multiwaySwitchInfo);
        multiwaySwitchInfo.setUpdatedBy(SecurityUtils.getCurrentUserLogin());
        multiwaySwitchInfo.setUpdateTime(Instant.now());
        return multiwaySwitchInfoRepository.save(multiwaySwitchInfo);
    }

    /**
     *  Get all the multiwaySwitchInfos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MultiwaySwitchInfo> findAll(Pageable pageable) {
        log.debug("Request to get all MultiwaySwitchInfos");
        return multiwaySwitchInfoRepository.findAll(pageable);
    }

    /**
     *  Get one multiwaySwitchInfo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MultiwaySwitchInfo findOne(Long id) {
        log.debug("Request to get MultiwaySwitchInfo : {}", id);
        return multiwaySwitchInfoRepository.findOne(id);
    }

    /**
     *  Delete the  multiwaySwitchInfo by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MultiwaySwitchInfo : {}", id);
        multiwaySwitchInfoRepository.delete(id);
    }
}
