package com.travelcompass.api.plan.repository;

import com.travelcompass.api.plan.domain.Plan;
import com.travelcompass.api.plan.domain.PlanLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanLocationRepository extends JpaRepository<PlanLocation, Long> {

    List<PlanLocation> findAllByPlanAndTravelDay(Plan plan, Long day);
    List<PlanLocation> findAllByPlan(Plan plan);
}
