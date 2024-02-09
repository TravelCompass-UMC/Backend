package com.travelcompass.api.location.service;

import com.travelcompass.api.global.entity.BaseEntity;
import com.travelcompass.api.location.converter.BusinessHoursConverter;
import com.travelcompass.api.location.domain.BusinessHours;
import com.travelcompass.api.location.domain.DayType;
import com.travelcompass.api.location.domain.Location;
import com.travelcompass.api.location.dto.BusinessHoursDto;
import com.travelcompass.api.location.repository.BusinessHoursRepository;
import java.util.List;
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
    public void save(Map<DayType, BusinessHoursDto.CreateBusinessHoursDto> businessHoursDtoMap,
            Location location) {
        for (DayType dayType : businessHoursDtoMap.keySet()) {
            BusinessHoursDto.CreateBusinessHoursDto businessHoursDto = businessHoursDtoMap.get(dayType);
            businessHoursRepository.save(
                    BusinessHoursConverter.toBusinessHours(dayType, businessHoursDto, location));
        }
    }

    public List<BusinessHours> findListByLocationId(Long locationId) {
        return businessHoursRepository.findAllByLocationId(locationId);
    }

}
