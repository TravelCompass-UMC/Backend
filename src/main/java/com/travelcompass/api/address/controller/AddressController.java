package com.travelcompass.api.address.controller;

import com.travelcompass.api.address.service.AddressService;
import com.travelcompass.api.address.service.Coordinates;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/address")
    public Coordinates address(){
        return addressService.getCoordinate();
    }
}
