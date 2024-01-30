package com.travelcompass.api.hashtag.converter;

import com.travelcompass.api.hashtag.domain.Hashtag;
import com.travelcompass.api.hashtag.domain.HashtagPlan;

import java.util.List;
import java.util.stream.Collectors;

public class HashtagConverter {
    public static Hashtag toHashtag(String tag){
        return Hashtag.builder().name(tag).build();
    }
}
