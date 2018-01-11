package com.emcloud.mi.service.impl;

import com.emcloud.mi.security.SecurityUtils;
import com.emcloud.mi.service.MeterInfoService;
import com.emcloud.mi.domain.MeterInfo;
import com.emcloud.mi.repository.MeterInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;


/**
 * Service Implementation for managing MeterInfo.
 */
@Service
@Transactional
public class MeterInfoServiceImpl implements MeterInfoService{

    private final Logger log = LoggerFactory.getLogger(MeterInfoServiceImpl.class);

    private final MeterInfoRepository meterInfoRepository;

    public MeterInfoServiceImpl(MeterInfoRepository meterInfoRepository) {
        this.meterInfoRepository = meterInfoRepository;
    }

    /**
     * Save a meterInfo.
     *
     * @param meterInfo the entity to save
     * @return the persisted entity
     */
    @Override
    public MeterInfo save(MeterInfo meterInfo) {
        log.debug("Request to save MeterInfo : {}", meterInfo);
        meterInfo.setCreatedBy(SecurityUtils.getCurrentUserLogin());
        meterInfo.setCreateTime(Instant.now());
        meterInfo.setUpdatedBy(SecurityUtils.getCurrentUserLogin());
        meterInfo.setUpdateTime(Instant.now());
        return meterInfoRepository.save(meterInfo);
    }

    /**
     * update a meterInfo.
     *
     * @param meterInfo the entity to update
     * @return the persisted entity
     */
    @Override
    public MeterInfo update(MeterInfo meterInfo) {
        log.debug("Request to save MeterInfo : {}", meterInfo);
        meterInfo.setUpdatedBy(SecurityUtils.getCurrentUserLogin());
        meterInfo.setUpdateTime(Instant.now());
        return meterInfoRepository.save(meterInfo);
    }


    /**
     *  Get all the meterInfos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MeterInfo> findAll(Pageable pageable) {
        log.debug("Request to get all MeterInfos");
        return meterInfoRepository.findAll(pageable);
    }
    /**
     *  Get all the meterInfos.
     *
     *  @param comPointCode the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<MeterInfo> findAllByComPointCode(String comPointCode){
        log.debug("Request to get all MeterInfo by comPointCode");
        return meterInfoRepository.findAllByComPointCode(comPointCode);
    }

    /**
     *  Get all the meterInfos.
     *
     *  @param meterType the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<MeterInfo> findAllByMeterType(String meterType){
        log.debug("Request to get all MeterInfo by meterType");
        return meterInfoRepository.findAllByComPointCode(meterType);
    }

    /**
     *  Get one meterInfo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MeterInfo findOne(Long id) {
        log.debug("Request to get MeterInfo : {}", id);
        return meterInfoRepository.findOne(id);
    }

    /**
     *  Delete the  meterInfo by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MeterInfo : {}", id);
        meterInfoRepository.delete(id);
    }
}
