package com.travelcompass.api.location.service;

import com.travelcompass.api.global.api_payload.ErrorCode;
import com.travelcompass.api.global.exception.GeneralException;
import com.travelcompass.api.location.domain.Location;
import com.travelcompass.api.location.domain.LocationType;
import com.travelcompass.api.location.repository.LocationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocationService {

    private final LocationRepository locationRepository;

    public Location findById(Long id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.LOCATION_NOT_FOUND));
    }

    public List<Location> findListByRegionId(Long regionId) {
        return locationRepository.findAllByRegionId(regionId);
    }

    public List<Location> findListByRegionIdAndLocationType(Long regionId, LocationType locationType) {
        return locationRepository.findAllByRegionIdAndLocationType(regionId, locationType);
    }

    public Location findByName(String name) {
        return locationRepository.findByName(name)
                .orElseThrow(() -> GeneralException.of(ErrorCode.LOCATION_NOT_FOUND));
    }

}
