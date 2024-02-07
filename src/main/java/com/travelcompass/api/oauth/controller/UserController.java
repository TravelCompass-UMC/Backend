package com.travelcompass.api.oauth.controller;

import com.travelcompass.api.global.api_payload.ApiResponse;
import com.travelcompass.api.global.api_payload.SuccessCode;
import com.travelcompass.api.oauth.jwt.JwtDto;
import com.travelcompass.api.oauth.jwt.JwtTokenUtils;
import com.travelcompass.api.oauth.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;

    // 로그아웃
    @DeleteMapping("/logout")
    public ApiResponse<Integer> logout(HttpServletRequest request) {
        userService.logout(request);
        return ApiResponse.onSuccess(SuccessCode.USER_LOGOUT_SUCCESS, 1);
    }

    // 토큰 재발급
    @PostMapping("/reissue")
    public ApiResponse<JwtDto> reissue(
            HttpServletRequest request
    ) {
        JwtDto jwt = userService.reissue(request);
        return ApiResponse.onSuccess(SuccessCode.USER_REISSUE_SUCCESS, jwt);
    }

    // 회원탈퇴
    @DeleteMapping("/me")
    public ApiResponse<Integer> deleteUser(Authentication auth) {
        userService.deleteUser(auth.getName());
        return ApiResponse.onSuccess(SuccessCode.USER_DELETE_SUCCESS, 1);
    }
}