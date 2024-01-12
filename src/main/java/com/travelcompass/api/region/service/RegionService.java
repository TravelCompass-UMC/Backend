package com.travelcompass.api.region.service;

import com.travelcompass.api.global.api_payload.ErrorCode;
import com.travelcompass.api.global.exception.GeneralException;
import com.travelcompass.api.region.domain.Region;
import com.travelcompass.api.region.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegionService {

    private final RegionRepository repository;

    public Region findRegionById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REGION_NOT_FOUND));
    }

}
