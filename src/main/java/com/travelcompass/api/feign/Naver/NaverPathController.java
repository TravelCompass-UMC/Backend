package com.travelcompass.api.feign.Naver;

import com.travelcompass.api.global.api_payload.ApiResponse;
import com.travelcompass.api.global.api_payload.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "여행계획 경로", description = "경로(자가용) 관련 api 입니다. - 양지원")
@RequiredArgsConstructor
@RestController
public class NaverPathController {

    private final NaverPathService naverPathService;

    @Operation(summary = "여행계획 경로", description = "장소 id를 파라미터로 주면, 자가용 소요시간을 반환하는 메서드입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PLAN_2031", description = "네이버 경로탐색이 완료되었습니다.(단위: 1/1000초)"),
    })
    @Parameters({
            @Parameter(name = "start", description = "출발지의 location-id"),
            @Parameter(name = "goal", description = "도착지의 location-id"),
    })
    @GetMapping("/getCarDuration") // http://dev.enble.site:8080/getCarDuration?start=1&goal=2
    public ApiResponse<Integer> getDuration(@RequestParam("start") Long startLocationId,
                                              @RequestParam("goal") Long goalLocationId) {

        // 파라미터로 받은 locaion-id값 좌표로 변환
        String start = naverPathService.getCoordinateById(startLocationId);
        String goal = naverPathService.getCoordinateById(goalLocationId);

        // 시작, 도착지 좌표 네이버 경로 서비스로 전달
        Integer duration = naverPathService.getDuration(start, goal);

        return ApiResponse.onSuccess(SuccessCode.PLAN_NAVER_GET_DURATION, duration);
    }
}