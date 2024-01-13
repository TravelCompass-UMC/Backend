package com.travelcompass.api.region.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.travelcompass.api.global.api_payload.ErrorCode;
import com.travelcompass.api.global.exception.GeneralException;
import com.travelcompass.api.region.domain.Region;
import com.travelcompass.api.region.repository.RegionRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RegionServiceTest {

    @InjectMocks
    private RegionService regionService;

    @Mock
    private RegionRepository regionRepository;

    @Test
    @DisplayName("findRegionById - Region 찾은 경우 Region 객체 반환")
    void region_찾기_성공() {
        // given
        Region expectedRegion = new Region();
        when(regionRepository.findById(anyLong())).thenReturn(Optional.of(expectedRegion));

        // when
        Region findRegion = regionService.findRegionById(1L);

        // then
        assertEquals(expectedRegion, findRegion);
        verify(regionRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("findRegionById - Region 못 찾은 경우 REGION_NOT_FOUND 에러 발생")
    void region_찾기_실패() {
        // given
        when(regionRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when
        GeneralException exception = assertThrows(GeneralException.class, () -> regionService.findRegionById(1L));

        // then
        assertEquals(ErrorCode.REGION_NOT_FOUND, exception.getCode());
        verify(regionRepository, times(1)).findById(anyLong());
    }

}
