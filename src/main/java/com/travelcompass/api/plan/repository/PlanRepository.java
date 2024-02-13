package com.travelcompass.api.plan.repository;

import com.travelcompass.api.plan.domain.Plan;
import com.travelcompass.api.region.domain.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long>, JpaSpecificationExecutor<Plan> {
    Optional<Plan> findByInviteCode(UUID inviteCode);

    List<Plan> findAllByRegion(Region region);
}
