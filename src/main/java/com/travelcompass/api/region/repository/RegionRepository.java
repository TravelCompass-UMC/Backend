package com.travelcompass.api.region.repository;

import com.travelcompass.api.region.domain.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {

}
