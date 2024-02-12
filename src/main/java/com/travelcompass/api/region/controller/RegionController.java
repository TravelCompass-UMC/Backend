package com.travelcompass.api.region.controller;

import com.travelcompass.api.global.api_payload.ApiResponse;
import com.travelcompass.api.region.converter.RegionConverter;
import com.travelcompass.api.region.dto.RegionResponseDto.DetailRegionDto;
import com.travelcompass.api.region.service.RegionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "지역", description = "지역 관련 api 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/regions")
public class RegionController {

    private final RegionService regionService;

    @Operation(summary = "지역 목록 조회", description = "지역 목록을 조회하는 메서드입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_200", description = "Success"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REGION_404", description = "지역을 찾을 수 없습니다.")
    })
    @GetMapping()
    public ApiResponse<List<DetailRegionDto>> getRegionList() {
        List<DetailRegionDto> findList = regionService.findAllRegions().stream()
                .map(RegionConverter::toRegionDto)
                .toList();
        return ApiResponse.onSuccess(findList);
    }

    @Operation(summary = "지역 상세 조회", description = "지역 상세 정보를 조회하는 메서드입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_200", description = "Success"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REGION_404", description = "지역을 찾을 수 없습니다.")
    })
    @Parameter(name = "regionId", description = "지역 id", required = true)
    @GetMapping("/{regionId}")
    public ApiResponse<DetailRegionDto> getRegionDetail(@PathVariable Long regionId) {
        return ApiResponse.onSuccess(RegionConverter.toRegionDto(regionService.findRegionById(regionId)));
    }

    @Operation(summary = "지역 이름으로 조회", description = "지역 이름으로 조회하는 메서드입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_200", description = "Success"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REGION_404", description = "지역을 찾을 수 없습니다.")
    })
    @Parameter(name = "name", description = "지역 이름", required = true)
    @GetMapping("/search")
    public ApiResponse<DetailRegionDto> getRegionByName(@RequestParam(name = "name") String name) {
        return ApiResponse.onSuccess(RegionConverter.toRegionDto(regionService.findRegionByName(name)));
    }

}
