package com.travelcompass.api.plan.service;

import com.travelcompass.api.oauth.domain.User;
import com.travelcompass.api.oauth.repository.UserRepository;
import com.travelcompass.api.plan.domain.Plan;
import com.travelcompass.api.plan.domain.PlanLike;
import com.travelcompass.api.plan.repository.PlanLikeRepository;
import com.travelcompass.api.plan.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlanLikeService {
    private final PlanLikeRepository planLikeRepository;
    private final PlanRepository planRepository;
    private final UserRepository userRepository;

    // 좋아요 토글 및 좋아요 수 조회
    @Transactional
    public Plan toggleLikeAndRetrieveCount(Plan plan, User user) {
        PlanLike existingLike = planLikeRepository.findByUserAndPlan(user, plan);

        if (existingLike != null) {
            // 이미 좋아요를 눌렀다면 좋아요 취소
            planLikeRepository.delete(existingLike);
        } else {
            // 좋아요 누르기
            planLikeRepository.save(PlanLike.builder().user(user).plan(plan).build());
        }

        // 좋아요 수 업데이트
        updateLikeCount(plan);
        return planRepository.save(plan);
    }

    // 좋아요 취소 및 좋아요 수 조회
    @Transactional
    public Plan cancelLikeAndRetrieveCount(Plan plan, User user) {
        PlanLike existingLike = planLikeRepository.findByUserAndPlan(user, plan);

        if (existingLike != null) {
            // 이미 좋아요를 눌렀다면 좋아요 취소
            planLikeRepository.delete(existingLike);

            // 좋아요 수 업데이트
            updateLikeCount(plan);
            return planRepository.save(plan);
        } else {
            // 취소할 좋아요가 없으면 그대로 반환
            return plan;
        }
    }

    // 좋아요 수 조회
    public Long getLikeCount(Plan plan) {
        return planLikeRepository.countAllByPlan(plan);
    }

    // 좋아요 수 업데이트
    private void updateLikeCount(Plan plan) {
        Long likeCount = planLikeRepository.countAllByPlan(plan);
        plan.updateLikeCount(likeCount);
    }
}
