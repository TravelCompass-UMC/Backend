package com.travelcompass.api.plan.repository;

import com.travelcompass.api.plan.domain.PlanGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanGroupRepository extends JpaRepository<PlanGroup, Long> {
}
