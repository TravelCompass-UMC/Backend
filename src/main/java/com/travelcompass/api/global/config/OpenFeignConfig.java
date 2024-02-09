package com.travelcompass.api.global.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = {"com.travelcompass.api.feign"})
class OpenFeignConfig {

}
