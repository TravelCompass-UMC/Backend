package com.travelcompass.api.hashtag.converter;

import com.travelcompass.api.hashtag.domain.Hashtag;
import com.travelcompass.api.hashtag.domain.HashtagPlan;

import java.util.List;
import java.util.stream.Collectors;

public class HashtagConverter {

    public static List<String> toHashtagNames(List<HashtagPlan> hashtagPlans){
        return hashtagPlans.stream().map(HashtagPlan::getHashtag).toList()
                .stream().map(Hashtag::getName).collect(Collectors.toList());
    }
}
