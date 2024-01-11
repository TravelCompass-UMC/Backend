package com.travelcompass.api.global.jwt;

import lombok.Data;

@Data
public class JwtRequestDto {
    private String username;
    private String password;
}

