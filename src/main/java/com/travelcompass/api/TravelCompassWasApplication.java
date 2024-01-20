package com.travelcompass.api;

import com.travelcompass.api.location.service.LocationScraper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TravelCompassWasApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelCompassWasApplication.class, args);

        // test
        LocationScraper locationScraper = new LocationScraper();


        locationScraper.scrapeLocations();
    }

}
