package com.travelcompass.api.location.converter;

import com.travelcompass.api.location.domain.Location;
import com.travelcompass.api.location.domain.LocationImage;

public class LocationImageConverter {

    public static LocationImage toLocationImage(String url, Location location) {
        return LocationImage.builder()
                .url(url)
                .location(location)
                .build();
    }

}
