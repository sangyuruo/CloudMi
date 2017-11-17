package com.emcloud.mi.repository;

import com.emcloud.mi.domain.MultiwaySwitch;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MultiwaySwitch entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MultiwaySwitchRepository extends JpaRepository<MultiwaySwitch, Long> {

}
