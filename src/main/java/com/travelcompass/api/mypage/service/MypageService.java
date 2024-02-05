package com.travelcompass.api.mypage.service;

import com.travelcompass.api.oauth.domain.User;
import com.travelcompass.api.plan.domain.Plan;
import com.travelcompass.api.plan.domain.PlanLike;
import com.travelcompass.api.plan.domain.PlanUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MypageService {

    public List<Plan> findPlansByUser(User user) {
        return user.getPlanUsers().stream().map(PlanUser::getPlan).toList();
    }

    public Page<Plan> convertPlanToPage(List<Plan> plans, Integer page, Integer pageSize){
        int start = page * pageSize;
        int end = Math.min(start + pageSize, plans.size());
        List<Plan> pagedPlans = plans.subList(start, end);

        return new PageImpl<>(pagedPlans, PageRequest.of(page, pageSize), plans.size());
    }
}
