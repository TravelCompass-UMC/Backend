package com.travelcompass.api.adress.service;

import com.travelcompass.api.adress.domain.Address;
import com.travelcompass.api.adress.repository.AddressRepository;
import com.travelcompass.api.global.exception.GeneralException;
import org.apache.commons.lang3.ObjectUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class AddressServiceTest {

    AddressService addressService;

    @MockBean
    AddressRepository addressRepository;

    @BeforeEach
    void setUp(){
        addressService = new AddressService(addressRepository);
    }

    @Test
    @DisplayName("주소 찾기 성공")
    void findAddressByCode(){
        //given
        Address address1 = Address.builder().addressCode(1).sidoCode("서울시")
                .sigunguCode("마포구").umdCode("동교동").legalDongCode(1).build();

        Address address2 = Address.builder().addressCode(2).sidoCode("전라남도")
                .sigunguCode("진도군").umdCode("지산면").riCode("지산리").legalDongCode(2).build();

        Mockito.when(addressRepository.findById(1)).thenReturn(Optional.ofNullable(address1));
        Mockito.when(addressRepository.findById(2)).thenReturn(Optional.ofNullable(address2));

        //when
        Address addressByCode1 = addressService.findAddressByCode(1);
        Address addressByCode2 = addressService.findAddressByCode(2);

        //then
        assertThat(addressByCode1.getAddressCode()).isEqualTo(1);
        System.out.println("address1 찾기 성공");
        assertThat(addressByCode2.getAddressCode()).isEqualTo(2);
        System.out.println("address2 찾기 성공");
    }

    @Test
    @DisplayName("주소 찾기 실패한 경우")
    void failToFindAddress(){
        //given
        Optional<Address> nullAddress = Optional.empty();
        Mockito.when(addressRepository.findById(1)).thenReturn(nullAddress);

        //when
        //then
        Assertions.assertThatThrownBy(
                () -> addressService.findAddressByCode(1)).isInstanceOf(GeneralException.class);
        System.out.println("GeneralException Error 발생");
    }

}
