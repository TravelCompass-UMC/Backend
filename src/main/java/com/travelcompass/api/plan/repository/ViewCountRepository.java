package com.travelcompass.api.plan.repository;

import com.travelcompass.api.plan.domain.Plan;
import com.travelcompass.api.plan.domain.ViewCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewCountRepository extends JpaRepository<ViewCount, Long> {
    Long countAllByPlan(Plan plan);
}
