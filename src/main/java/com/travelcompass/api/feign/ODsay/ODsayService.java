package com.travelcompass.api.feign.ODsay;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelcompass.api.global.api_payload.ErrorCode;
import com.travelcompass.api.global.exception.GeneralException;
import com.travelcompass.api.location.domain.Location;
import com.travelcompass.api.location.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ODsayService {

    private final ODsayDurationClient oDsayDurationClient;
    private final LocationService locationService;

    @Value("${odsay.directions.api-key}")
    private String apiKey;

    public Integer getDurationByODsay(Location startLocation, Location endLocation) {

        System.out.println(apiKey);

        ResponseEntity<String> response = oDsayDurationClient.getResult(apiKey,
                startLocation.getLongitude().toString(), startLocation.getLatitude().toString(),
                endLocation.getLongitude().toString(), endLocation.getLatitude().toString());

        // ResponseEntity에서 응답 body를 가져옴
        String responseBody = response.getBody();

        // ObjectMapper를 사용하여 JSON 문자열을 JsonNode 객체로 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(responseBody);
        } catch (Exception e) {
            log.error("Failed to parse JSON response", e);
            throw GeneralException.of(ErrorCode.FAIL_TO_PARSE_JSON);
        }

        // JsonNode에서 "result.path[0].info.totalTime" 경로의 값을 가져와서 int로 변환합니다.
        int duration = jsonNode.path("result")
                .path("path").path(0).path("info")
                .path("totalTime").asInt();

        return duration;
    }
}

