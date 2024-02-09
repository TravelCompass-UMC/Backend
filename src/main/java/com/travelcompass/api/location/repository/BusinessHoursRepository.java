package com.travelcompass.api.location.repository;

import com.travelcompass.api.location.domain.BusinessHours;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessHoursRepository extends JpaRepository<BusinessHours, Long> {

    List<BusinessHours> findAllByLocationId(Long locationId);

}
