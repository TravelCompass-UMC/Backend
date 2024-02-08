package com.travelcompass.api.mypage.converter;

import com.travelcompass.api.address.domain.Address;
import com.travelcompass.api.location.domain.Location;
import com.travelcompass.api.mypage.dto.MypageResponseDto;
import com.travelcompass.api.mypage.dto.MypageResponseDto.MyInfoDto;
import com.travelcompass.api.mypage.dto.MypageResponseDto.MyLocationListResponseDto;
import com.travelcompass.api.mypage.dto.MypageResponseDto.MypageLocationDto;
import com.travelcompass.api.oauth.domain.User;
import com.travelcompass.api.plan.converter.PlanConverter;
import com.travelcompass.api.plan.domain.Plan;
import com.travelcompass.api.plan.dto.PlanResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public class MypageConverter {

    public static MyInfoDto myInfoDto(User user){
        return MyInfoDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    public static MypageLocationDto mypageLocationDto(Location location){
        return MypageLocationDto.builder()
                .id(location.getId())
                .name(location.getName())
                .locationType(String.valueOf(location.getLocationInfo().getLocationType()))
                .likeCount(location.getLikeCount())
                .address(location.getRoadNameAddress())
                .star(location.getStar())
                .build();
    }

    public static MyLocationListResponseDto myLocationListResponseDto(Page<Location> locations){
        List<MypageLocationDto> locationDtos = locations.stream().map(MypageConverter::mypageLocationDto).toList();

        return MyLocationListResponseDto.builder()
                .isLast(locations.isLast())
                .isFirst(locations.isFirst())
                .totalPage(locations.getTotalPages())
                .totalElements(locations.getTotalElements())
                .listSize(locations.getSize())
                .locationList(locationDtos)
                .build();
    }

}
