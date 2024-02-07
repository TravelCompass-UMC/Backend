package com.travelcompass.api.location.service;

import com.travelcompass.api.global.entity.BaseEntity;
import com.travelcompass.api.location.converter.BusinessHoursConverter;
import com.travelcompass.api.location.domain.DayType;
import com.travelcompass.api.location.domain.Location;
import com.travelcompass.api.location.dto.BusinessHoursDto;
import com.travelcompass.api.location.repository.BusinessHoursRepository;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BusinessHoursService extends BaseEntity {

    private final BusinessHoursRepository businessHoursRepository;

    @Transactional
    public void saveBusinessHours(Map<DayType, BusinessHoursDto> businessHoursDtoMap, Location location) {
        for (DayType dayType : businessHoursDtoMap.keySet()) {
            BusinessHoursDto businessHoursDto = businessHoursDtoMap.get(dayType);
            businessHoursRepository.save(
                    BusinessHoursConverter.toBusinessHours(dayType, businessHoursDto, location));
        }
    }

}
