package com.emcloud.mi.repository;

import com.emcloud.mi.domain.MultiwaySwitchInfo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MultiwaySwitchInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MultiwaySwitchInfoRepository extends JpaRepository<MultiwaySwitchInfo, Long> {

}
