package com.travelcompass.api.location.repository;

import com.travelcompass.api.location.domain.Location;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Optional<Location> findByName(String name);

    List<Location> findAllByRegionId(Long regionId);
}
