package com.travelcompass.api.hashtag.service;

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
import java.util.stream.Collectors;

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

    public List<Hashtag> findHashtagsByPlan(Plan plan){
        List<HashtagPlan> hashtagPlans = hashtagPlanRepository.findAllByPlan(plan);
        return hashtagPlans.stream().map(HashtagPlan::getHashtag).collect(Collectors.toList());
    }

    /*
    들어온 태그명들을 보고 해쉬태그 디비에 없으면 새로 생성해서 저장 후
    들어온 태그명들을 객체로 반환
     */
    public List<Hashtag> createNewHashtags(List<String> tags){
        List<Hashtag> existingTags = hashtagRepository.findByNameIn(tags);

        List<Hashtag> newHashtags = tags.stream()
                .map(tag -> existingTags.stream()
                        .filter(hashtag -> tag.equals(hashtag.getName()))
                        .findFirst()
                        .orElseGet(() -> HashtagConverter.toHashtag(tag)))
                .collect(Collectors.toList());

        hashtagRepository.saveAll(newHashtags);

        return tags.stream().map(HashtagConverter::toHashtag).collect(Collectors.toList());
    }
}
