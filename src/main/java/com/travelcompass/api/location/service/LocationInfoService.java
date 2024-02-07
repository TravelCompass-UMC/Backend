package com.travelcompass.api.location.service;

import com.travelcompass.api.location.domain.LocationInfo;
import com.travelcompass.api.location.repository.LocationInfoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LocationInfoService {

    private final LocationInfoRepository locationInfoRepository;

    public List<LocationInfo> findAllLocationInfo() {
        return locationInfoRepository.findAll();
    }

}
