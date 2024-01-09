package com.travelcompass.api.adress.service;

import com.travelcompass.api.adress.domain.Address;
import com.travelcompass.api.adress.repository.AddressRepository;
import com.travelcompass.api.global.api_payload.ErrorCode;
import com.travelcompass.api.global.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository repository;

    public Address findAddressByCode(Integer code){
        Address address = repository.findById(code).orElseThrow(
                () -> new GeneralException(ErrorCode.ADDRESS_NOT_FOUND)
        );
        return address;
    }

}
