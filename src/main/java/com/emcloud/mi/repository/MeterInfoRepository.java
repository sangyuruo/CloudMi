package com.emcloud.mi.repository;

import com.emcloud.mi.domain.MeterInfo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the MeterInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MeterInfoRepository extends JpaRepository<MeterInfo, Long> {
    List<MeterInfo> findAllByComPointCode(String comPointCode);
    List<MeterInfo> findByMeterType(String meterType);
}
