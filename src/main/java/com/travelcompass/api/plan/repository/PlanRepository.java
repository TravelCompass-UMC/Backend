package com.travelcompass.api.plan.repository;

import com.travelcompass.api.plan.domain.Plan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    Optional<Plan> findByInviteCode(UUID inviteCode);

    Page<Plan> findAll(PageRequest pageRequest);
}
