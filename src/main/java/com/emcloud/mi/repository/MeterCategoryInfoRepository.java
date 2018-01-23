package com.emcloud.mi.repository;

import com.emcloud.mi.domain.MeterCategoryInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the MeterCategoryInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MeterCategoryInfoRepository extends JpaRepository<MeterCategoryInfo, Long> {
    Page<MeterCategoryInfo> findAllByMeterTypeContaining(Pageable pageable, String meterType);
    List<MeterCategoryInfo> findByMeterType(String meterType);
}
