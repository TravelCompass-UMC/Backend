package com.travelcompass.api.location.service;

import com.travelcompass.api.global.api_payload.ErrorCode;
import com.travelcompass.api.global.exception.GeneralException;
import com.travelcompass.api.location.converter.LocationImageConverter;
import com.travelcompass.api.location.domain.Location;
import com.travelcompass.api.location.domain.LocationImage;
import com.travelcompass.api.location.repository.LocationImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LocationImageService {

    private final LocationImageRepository locationImageRepository;

    public void saveImageUrl(String url, Location location) {
        locationImageRepository.save(LocationImageConverter.toLocationImage(url, location));
    }

    public String findImageUrlByLocationId(Long locationId) {
        LocationImage locationImage = locationImageRepository.findByLocationId(locationId)
                .orElseThrow(() -> GeneralException.of(ErrorCode.LOCATION_IMAGE_NOT_FOUND));
        return locationImage.getUrl();
    }

}
