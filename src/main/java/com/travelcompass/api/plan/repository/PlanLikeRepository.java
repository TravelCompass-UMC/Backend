package com.travelcompass.api.plan.repository;

import com.travelcompass.api.oauth.domain.User;
import com.travelcompass.api.plan.domain.Plan;
import com.travelcompass.api.plan.domain.PlanLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanLikeRepository extends JpaRepository<PlanLike, Long> {
    Long countAllByPlan(Plan plan);

    // 사용자와 계획에 대한 좋아요 정보 조회
    PlanLike findByUserAndPlan(User user, Plan plan);
}
