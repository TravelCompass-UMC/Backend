package com.travelcompass.api.location.domain;

import com.travelcompass.api.address.domain.Address;
import com.travelcompass.api.global.entity.BaseEntity;
import com.travelcompass.api.region.domain.Region;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double star;

    private String roadNameAddress;

    private String tel;

    private Double latitude; // 위도

    private Double longitude; // 경도

    private String businessHoursEtc; // 영업시간 추가정보

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "ADDRESS_CODE")
//    private Address address;

}
