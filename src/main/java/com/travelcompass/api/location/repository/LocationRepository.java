package com.travelcompass.api.location.repository;

import com.travelcompass.api.location.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {

}
