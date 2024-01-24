package com.travelcompass.api.address.service;

import com.travelcompass.api.address.domain.Address;
import com.travelcompass.api.address.repository.AddressRepository;
import com.travelcompass.api.global.api_payload.ErrorCode;
import com.travelcompass.api.global.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AddressService {

    private final String uri = "https://dapi.kakao.com/v2/local/search/address.json";

    @Value("${kakao.local.key}")
    private String kakaoLocalKey;

    private final AddressRepository repository;

    public Address findAddressByCode(Integer code){
        Address address = repository.findById(code).orElseThrow(
                () -> GeneralException.of(ErrorCode.ADDRESS_NOT_FOUND)
        );
        return address;
    }

    public Coordinates getCoordinate(){

        // 요청에 담아보낼 API key와 address 생성
        String apiKey = "KakaoAK " + kakaoLocalKey;
        String address = "서울시 강남구 테헤란로 131"; // 대체하여 사용

        // 요청 헤더에 만들기, Authorization 헤더 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        // HTTP 요청에 포함할 쿼리 작성
        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(uri)
                .queryParam("query",address)
                .build();

        // API 요청 보내기 위해 spring에서 제공하는 RestTemplate 사용
        RestTemplate restTemplate = new RestTemplate();
        // exchange(uri를 String 형태로, Http 메서드, HttpEntity, 반환받을 변수 형식)
        ResponseEntity<String> response = restTemplate.exchange(uriComponents.toString(), HttpMethod.GET, entity, String.class);

        // API Response로부터 body 뽑아내기
        String body = response.getBody();
        JSONObject json = new JSONObject(body);

        // body에서 좌표 뽑아내기
        JSONArray documents = json.getJSONArray("documents");
        String x = documents.getJSONObject(0).getString("x");
        String y = documents.getJSONObject(0).getString("y");

        return new Coordinates(x, y);
    }

}
