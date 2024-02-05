package com.travelcompass.api.mypage.controller;

import com.travelcompass.api.global.api_payload.ApiResponse;
import com.travelcompass.api.global.api_payload.SuccessCode;
import com.travelcompass.api.mypage.converter.MypageConverter;
import com.travelcompass.api.mypage.dto.MypageResponseDto;
import com.travelcompass.api.mypage.dto.MypageResponseDto.MyInfoDto;
import com.travelcompass.api.mypage.service.MypageService;
import com.travelcompass.api.oauth.domain.User;
import com.travelcompass.api.oauth.jwt.CustomUserDetails;
import com.travelcompass.api.oauth.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "마이페이지", description = "마이페이지 관련 api 입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/me")
public class MypageController {

    private final MypageService mypageService;
    private final UserService userService;

    @GetMapping("/info")
    public ApiResponse<MyInfoDto> findMyInfo(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        return ApiResponse.onSuccess(SuccessCode.MYPAGE_INFO_VIEW_SUCCESS, MypageConverter.myInfoDto(user));
    }

}
