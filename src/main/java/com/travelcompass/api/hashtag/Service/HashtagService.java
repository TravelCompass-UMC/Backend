package com.travelcompass.api.hashtag.Service;

import com.travelcompass.api.global.api_payload.ErrorCode;
import com.travelcompass.api.global.exception.GeneralException;
import com.travelcompass.api.hashtag.converter.HashtagConverter;
import com.travelcompass.api.hashtag.domain.Hashtag;
import com.travelcompass.api.hashtag.domain.HashtagPlan;
import com.travelcompass.api.hashtag.repository.HashtagPlanRepository;
import com.travelcompass.api.hashtag.repository.HashtagRepository;
import com.travelcompass.api.plan.domain.Plan;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HashtagService {

    private final HashtagRepository hashtagRepository;
    private final HashtagPlanRepository hashtagPlanRepository;

    public Hashtag findHashtagById(Long hashtagId){
        return hashtagRepository.findById(hashtagId)
                .orElseThrow(()-> GeneralException.of(ErrorCode.HASHTAG_NOT_FOUND));
    }

    public List<String> findHashtagNamesByPlan(Plan plan){
        List<HashtagPlan> hashtagPlans = hashtagPlanRepository.findAllByPlan(plan);
        return HashtagConverter.toHashtagNames(hashtagPlans);
    }
}
