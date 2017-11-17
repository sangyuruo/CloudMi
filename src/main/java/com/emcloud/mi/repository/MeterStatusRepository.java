package com.emcloud.mi.repository;

import com.emcloud.mi.domain.MeterStatus;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MeterStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MeterStatusRepository extends JpaRepository<MeterStatus, Long> {

}
