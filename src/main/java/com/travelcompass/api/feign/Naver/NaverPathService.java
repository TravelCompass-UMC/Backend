package com.travelcompass.api.feign.Naver;

import com.travelcompass.api.location.service.LocationService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class NaverPathService {

    private final NaverDurationClient naverDurationClient;
    private final String clientId;
    private final String clientSecret;
    private final LocationService locationService;

    public NaverPathService(NaverDurationClient naverDurationClient,
                            LocationService locationService,
                            @Value("${naver.directions.client-id}") String clientId,
                            @Value("${naver.directions.client-secret}") String clientSecret) {
        this.naverDurationClient = naverDurationClient;
        this.locationService = locationService;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public String getCoordinateById(Long id) {

        String longitude = locationService.findById(id).getLongitude().toString();
        String latitude = locationService.findById(id).getLatitude().toString();

        return longitude + "," + latitude;
    }

    public int getDuration(String start, String goal) {
        // 네이버 API에 요청을 보내기 전에 클라이언트 ID와 시크릿을 헤더에 설정
        ResponseEntity<String> response = naverDurationClient.getDuration(start, goal, clientId, clientSecret);

        // API Response로부터 body 뽑아내기
        String body = response.getBody();
        JSONObject json = new JSONObject(body);

        // body에서 duration 추출하기
        int duration = json.getJSONObject("route").getJSONArray("traoptimal")
                .getJSONObject(0).getJSONObject("summary").getInt("duration");

        return duration;
    }
}