package com.travelcompass.api.location.repository;

import com.travelcompass.api.location.domain.Location;
import com.travelcompass.api.location.domain.LocationType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Optional<Location> findByName(String name);

    List<Location> findAllByRegionId(Long regionId);

    @Query("select l from Location l join l.locationInfo li where l.region.id = :regionId and li.locationType = :locationType")
    List<Location> findAllByRegionIdAndLocationType(Long regionId, LocationType locationType);
}
