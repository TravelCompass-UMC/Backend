package com.travelcompass.api.location.repository;

import com.travelcompass.api.location.domain.Location;
import com.travelcompass.api.location.domain.LocationImage;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationImageRepository extends JpaRepository<LocationImage, Long> {

    Optional<LocationImage> findByLocationId(Long locationId);
}
