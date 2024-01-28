package com.travelcompass.api.location.repository;

import com.travelcompass.api.location.domain.BusinessHours;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessHoursRepository extends JpaRepository<BusinessHours, Long> {

}
