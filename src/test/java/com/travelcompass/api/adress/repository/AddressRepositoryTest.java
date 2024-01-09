package com.travelcompass.api.adress.repository;

import com.travelcompass.api.adress.domain.Address;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AddressRepositoryTest {

    @Autowired
    AddressRepository repository;

    @Test
    @DisplayName("주소가 DB에 잘 저장됐는지 확인하는 테스트")
    void saveAddress(){
        //given
        Address address1 = Address.builder().addressCode(1).sidoCode("서울시")
                .sigunguCode("마포구").umdCode("동교동").legalDongCode(1).build();

        Address address2 = Address.builder().addressCode(2).sidoCode("전라남도")
                .sigunguCode("진도군").umdCode("지산면").riCode("지산리").legalDongCode(2).build();

        //when
        Address save1 = repository.save(address1);
        Address save2 = repository.save(address2);

        //then
        Assertions.assertThat(save1.getAddressCode()).isEqualTo(address1.getAddressCode());
        System.out.println("address1 저장 성공");
        Assertions.assertThat(save2.getAddressCode()).isEqualTo(address2.getAddressCode());
        System.out.println("address2 저장 성공");
        Assertions.assertThat(repository.findAll().size()).isEqualTo(2);
        System.out.println("총2개 검색 성공");
    }

}
