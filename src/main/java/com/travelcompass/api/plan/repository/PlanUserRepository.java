package com.travelcompass.api.plan.repository;

import com.travelcompass.api.plan.domain.PlanUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanUserRepository extends JpaRepository<PlanUser, Long> {
}
