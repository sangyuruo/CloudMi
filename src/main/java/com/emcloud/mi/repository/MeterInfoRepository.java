package com.emcloud.mi.repository;

import com.emcloud.mi.domain.MeterInfo;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MeterInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MeterInfoRepository extends JpaRepository<MeterInfo, Long> {

}
