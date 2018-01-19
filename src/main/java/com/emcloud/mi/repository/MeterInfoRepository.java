package com.emcloud.mi.repository;

import com.emcloud.mi.domain.MeterInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    Page<MeterInfo> findAllByCompanyCodeAndOrganizationCode(String companyCode, String organizationCode,Pageable pageable);
    MeterInfo findByMeterCode(String meterCode);

    /*MeterInfo findOneMeterInfo(String meterCode, String comPointCode, Integer registerCode);*/
    MeterInfo findByMeterCodeAndComPointCodeAndRegisterCode(String meterCode, String comPointCode, Integer registerCode);

    Page<MeterInfo> findAllByCompanyCodeAndOrganizationCodeStartingWith(String companyCode, String organizationCode,Pageable pageable);


}
