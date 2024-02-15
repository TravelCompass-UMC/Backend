package com.travelcompass.api.feign.ODsay;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ODsayDurationClient", url = "${odsay.directions.api-url}")
public interface ODsayDurationClient {

    @GetMapping("")
    ResponseEntity<String> getResult(@RequestParam("apiKey") String apiKey,
                                     @RequestParam("SX") String startX,
                                     @RequestParam("SY") String startY,
                                     @RequestParam("EX") String endX,
                                     @RequestParam("EY") String endY);

}
