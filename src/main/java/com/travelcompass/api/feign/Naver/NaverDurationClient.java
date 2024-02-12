package com.travelcompass.api.feign.Naver;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "naverDurationClient", url = "${naver.directions.api-url}")
public interface NaverDurationClient {

    @GetMapping("/v1/driving")
    ResponseEntity<String> getDuration(@RequestParam("start") String start,
                                         @RequestParam("goal") String goal,
                                         @RequestHeader("X-NCP-APIGW-API-KEY-ID") String clientId,
                                         @RequestHeader("X-NCP-APIGW-API-KEY") String clientSecret);
}
/* 요청 예시
curl -X GET "https://naveropenapi.apigw.ntruss.com/map-direction/v1/driving?start=127.12345,37.12345&goal=128.12345,37.12345" \
    -H "X-NCP-APIGW-API-KEY-ID: g4444lcndr" \
    -H "X-NCP-APIGW-API-KEY: qXHf876pnArw9AQ36pfsfVmL0kqCB8ppEqj5A2Lm"
 */