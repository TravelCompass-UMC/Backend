package com.travelcompass.api.plan.repository;

import com.travelcompass.api.plan.domain.Plan;
import com.travelcompass.api.plan.domain.PlanLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanLikeRepository extends JpaRepository<PlanLike, Long> {
    Long countAllByPlan(Plan plan);
}
