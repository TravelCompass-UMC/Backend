package com.travelcompass.api.mypage.converter;

import com.travelcompass.api.mypage.dto.MypageResponseDto;
import com.travelcompass.api.mypage.dto.MypageResponseDto.MyInfoDto;
import com.travelcompass.api.oauth.domain.User;

public class MypageConverter {

    public static MyInfoDto myInfoDto(User user){
        return MyInfoDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

}
