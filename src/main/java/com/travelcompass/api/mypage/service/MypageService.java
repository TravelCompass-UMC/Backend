package com.travelcompass.api.mypage.service;

import com.travelcompass.api.oauth.domain.User;
import com.travelcompass.api.plan.domain.Plan;
import com.travelcompass.api.plan.domain.PlanUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MypageService {

    public List<Plan> findUserPlans(User user) {
        return user.getPlanUsers().stream().map(PlanUser::getPlan).collect(Collectors.toList());
    }
}
