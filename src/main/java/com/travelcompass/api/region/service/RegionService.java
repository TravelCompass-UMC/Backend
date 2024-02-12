package com.travelcompass.api.region.service;

import com.travelcompass.api.global.api_payload.ErrorCode;
import com.travelcompass.api.global.exception.GeneralException;
import com.travelcompass.api.region.domain.Region;
import com.travelcompass.api.region.repository.RegionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegionService {

    private final RegionRepository repository;

    public List<Region> findAllRegions() {
        return repository.findAll();
    }

    public Region findRegionById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REGION_NOT_FOUND));
    }

    public Region findRegionByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REGION_NOT_FOUND));
    }

}
