package com.travelcompass.api.global.controller;

import com.travelcompass.api.oauth.jwt.JwtTokenUtils;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.*;

// 토큰 확인 - 주소값으로 던지기
@RestController
@RequestMapping("token")
public class TokenController {
    private final JwtTokenUtils jwtTokenUtils;

    public TokenController(
            JwtTokenUtils jwtTokenUtils
    ) {
        this.jwtTokenUtils = jwtTokenUtils;
    }

    @GetMapping("/val")
    public Claims val(@RequestParam("token") String jwt) {
        return jwtTokenUtils.parseClaims(jwt);
    }
}