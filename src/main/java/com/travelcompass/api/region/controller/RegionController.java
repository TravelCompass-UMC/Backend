package com.travelcompass.api.region.controller;

import com.travelcompass.api.global.api_payload.ApiResponse;
import com.travelcompass.api.region.converter.RegionConverter;
import com.travelcompass.api.region.dto.RegionResponseDto.DetailRegionDto;
import com.travelcompass.api.region.service.RegionService;
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

    @GetMapping()
    public ApiResponse<List<DetailRegionDto>> getRegionList() {
        List<DetailRegionDto> findList = regionService.findAllRegions().stream()
                .map(RegionConverter::toRegionDto)
                .toList();
        return ApiResponse.onSuccess(findList);
    }

    @GetMapping("/{regionId}")
    public ApiResponse<DetailRegionDto> getRegionDetail(@PathVariable Long regionId) {
        return ApiResponse.onSuccess(RegionConverter.toRegionDto(regionService.findRegionById(regionId)));
    }

    @GetMapping("/search")
    public ApiResponse<DetailRegionDto> getRegionByName(@RequestParam(name = "name") String name) {
        return ApiResponse.onSuccess(RegionConverter.toRegionDto(regionService.findRegionByName(name)));
    }

}
