package com.travelcompass.api.adress.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class AddressDomainTest {

    /*
     * 리가 없는경우
     * */
    @Test
    @DisplayName("주소가 생성되었는지 확인하는 테스트 1")
    void createAddress1(){
        //given
        Address address1 = Address.builder()
                .addressCode(1)
                .sidoCode("서울시")
                .sigunguCode("마포구")
                .umdCode("동교동")
                .legalDongCode(1)
                .build();

        //when
        //then
        assertThat(address1.getAddressCode()).isEqualTo(1);
        assertThat(address1.getSidoCode()).isEqualTo("서울시");
        assertThat(address1.getSigunguCode()).isEqualTo("마포구");
        assertThat(address1.getUmdCode()).isEqualTo("동교동");
        assertThat(address1.getRiCode()).isNull();
        assertThat(address1.getLegalDongCode()).isEqualTo(1);
        System.out.println("테스트1 성공");
    }


    /*
    * 리가 있는경우
    * */
    @Test
    @DisplayName("주소가 생성되었는지 확인하는 테스트 2")
    void createAddress2(){
        //given
        Address address2 = Address.builder()
                .addressCode(2)
                .sidoCode("전라남도")
                .sigunguCode("진도군")
                .umdCode("지산면")
                .riCode("지산리")
                .legalDongCode(2)
                .build();

        //when
        //then
        assertThat(address2.getAddressCode()).isNotEqualTo(1);
        assertThat(address2.getAddressCode()).isEqualTo(2);
        assertThat(address2.getSidoCode()).isEqualTo("전라남도");
        assertThat(address2.getSigunguCode()).isEqualTo("진도군");
        assertThat(address2.getUmdCode()).isEqualTo("지산면");
        assertThat(address2.getRiCode()).isNotNull();
        assertThat(address2.getRiCode()).isEqualTo("지산리");
        assertThat(address2.getLegalDongCode()).isEqualTo(2);
        System.out.println("테스트2 성공");
    }
}