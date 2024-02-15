package com.travelcompass.api.feign.ODsay;

import com.amazonaws.Response;
import com.travelcompass.api.feign.Naver.NaverPathService;
import com.travelcompass.api.global.api_payload.ApiResponse;
import com.travelcompass.api.global.api_payload.SuccessCode;
import com.travelcompass.api.location.domain.Location;
import com.travelcompass.api.location.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "여행계획 경로", description = "경로(대중교통) 관련 api 입니다. - 김현우")
@RequiredArgsConstructor
@RestController
public class ODsayController {

    private final ODsayService oDsayService;
    private final LocationService locationService;

    @Operation(summary = "여행계획 경로", description = "장소 id를 파라미터로 주면, 대중교통 경로 정보를 반환하는 메서드입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PLAN_2003", description = "대중교통 경로탐색이 완료되었습니다.(단위: 분)"),
    })
    @Parameters({
            @Parameter(name = "start", description = "출발지의 location-id"),
            @Parameter(name = "goal", description = "도착지의 location-id"),
    })
    @GetMapping("/getPublicDuration")
    public ApiResponse<Integer> getDuration(@RequestParam("start") Long startLocationId,
                                                           @RequestParam("goal") Long goalLocationId) {

        // 파라미터로 받은 locaion-id값 좌표로 변환
        Location startLocation = locationService.findById(startLocationId);
        Location endLocation = locationService.findById(goalLocationId);

        Integer duration = oDsayService.getDurationByODsay(startLocation, endLocation);

        return ApiResponse.onSuccess(SuccessCode.PLAN_PUBLIC_GET_DURATION, duration);
    }
}