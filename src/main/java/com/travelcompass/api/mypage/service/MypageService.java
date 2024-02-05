package com.travelcompass.api.mypage.service;

import com.travelcompass.api.global.api_payload.ApiResponse;
import com.travelcompass.api.global.api_payload.ErrorCode;
import com.travelcompass.api.global.exception.GeneralException;
import com.travelcompass.api.location.domain.Location;
import com.travelcompass.api.location.domain.LocationLike;
import com.travelcompass.api.location.domain.LocationType;
import com.travelcompass.api.oauth.domain.User;
import com.travelcompass.api.oauth.jwt.CustomUserDetails;
import com.travelcompass.api.plan.domain.Plan;
import com.travelcompass.api.plan.domain.PlanLike;
import com.travelcompass.api.plan.domain.PlanUser;
import com.travelcompass.api.plan.dto.PlanResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MypageService {

    public List<Plan> findPlansByUser(User user) {
        return user.getPlanUsers().stream().map(PlanUser::getPlan).toList();
    }

    public List<Plan> findLikedPlans(User user){
        return user.getPlanLikes().stream().map(PlanLike::getPlan).toList();
    }

    public List<Location> findLikedLocations(User user){
        return user.getLocaionLikes().stream().map(LocationLike::getLocation).toList();
    }

    public Page<Plan> convertPlanToPage(List<Plan> plans, Integer page, Integer pageSize){
        int start = page * pageSize;
        int end = Math.min(start + pageSize, plans.size());
        List<Plan> pagedPlans = plans.subList(start, end);

        return new PageImpl<>(pagedPlans, PageRequest.of(page, pageSize), plans.size());
    }

    // location 리스트를 location Type에 의해 필터링 함
    public List<Location> filterLocationsByType(List<Location> locations, String locationType){

        if (locationType.equals("ALL")){
            return locations;
        } else if (LocationType.valueOf(locationType).equals(LocationType.ATTRACTION)
                || LocationType.valueOf(locationType).equals(LocationType.RESTAURANT))
        {
            return locations.stream()
                    .filter(location -> location.getLocationType() == LocationType.valueOf(locationType))
                    .toList();
        } else {
            throw GeneralException.of(ErrorCode.MYPAGE_WRONG_LOCATION_TYPE);
        }
    }

    // sort 메서드 따로 구현
    public List<Location> sortLocationsByWay(List<Location> locations, String way){
        if (way.equals("like")){
            return locations.stream()
                    .sorted(Comparator.comparingLong(Location::getLikeCount).reversed())
                    .toList();
        } else if (way.equals("rate")) {
            // location 에 별점이 없어서 일단 id의 역순, 즉 최신순으로 정렬함
            return locations.stream()
                    .sorted(Comparator.comparingLong(Location::getId).reversed())
                    .toList();
        } else {
            throw GeneralException.of(ErrorCode.MYPAGE_WRONG_SORTING_WAY);
        }
    }

    public Page<Location> convertLocationToPage(List<Location> locations, Integer page, Integer pageSize) {
        int start = page * pageSize;
        int end = Math.min(start + pageSize, locations.size());
        List<Location> pagedLocations = locations.subList(start, end);

        return new PageImpl<>(pagedLocations, PageRequest.of(page, pageSize), locations.size());
    }
}
