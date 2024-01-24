package com.travelcompass.api.hashtag.repository;

import com.travelcompass.api.hashtag.domain.HashtagPlan;
import com.travelcompass.api.plan.domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HashtagPlanRepository extends JpaRepository<HashtagPlan, Long> {

    List<HashtagPlan> findAllByPlan(Plan plan);
}
