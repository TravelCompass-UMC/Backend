package com.travelcompass.api.location.repository;

import com.travelcompass.api.location.domain.LocationInfo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationInfoRepository extends JpaRepository<LocationInfo, Long> {
    Optional<LocationInfo> findByLocationId(Long locationId);
}
