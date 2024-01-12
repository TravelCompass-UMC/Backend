package com.travelcompass.api.address;

import com.travelcompass.api.address.domain.Address;
import com.travelcompass.api.address.repository.AddressRepository;
import com.travelcompass.api.address.service.AddressService;
import com.travelcompass.api.global.exception.GeneralException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AddressTest {
    //통합 테스트
    @Autowired
    AddressRepository addressRepository;

    @Autowired
    AddressService addressService;

    @BeforeEach
    void setUp() {
        Address address1 = Address.builder().addressCode(1).sidoCode("서울시")
                .sigunguCode("마포구").umdCode("동교동").build();

        Address address2 = Address.builder().addressCode(2).sidoCode("전라남도")
                .sigunguCode("진도군").umdCode("지산면").riCode("지산리").build();

        Address save1 = addressRepository.save(address1);
        Address save2 = addressRepository.save(address2);
    }


    @Test
    @DisplayName("주소 찾기 성공한 경우")
    void findSuccess(){
        Address addressByCode1 = addressService.findAddressByCode(1);
        Address addressByCode2 = addressService.findAddressByCode(2);

        assertThat(addressByCode1.getAddressCode()).isEqualTo(1);
        assertThat(addressByCode1).isInstanceOf(Address.class);
        System.out.println("address1 찾기 성공");
        assertThat(addressByCode2.getAddressCode()).isEqualTo(2);
        assertThat(addressByCode2).isInstanceOf(Address.class);
        System.out.println("address2 찾기 성공");
    }

    @Test
    @DisplayName("주소 찾기 실패한 경우")
    void failToFind(){
        assertThatThrownBy(() -> addressService.findAddressByCode(3)).isInstanceOf(GeneralException.class);
        System.out.println("GeneralException Error 발생");
    }

}
