package com.travelcompass.api.plan.domain;

import com.travelcompass.api.global.entity.BaseEntity;
import com.travelcompass.api.region.domain.Region;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Plan extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDate startDate;

    private LocalDate endDate;

    private UUID inviteCode; // 초대를 위한 유니크한 코드

    private Long adultCount;
    private Long childCount;

    private Long hits; // 조회수

    @Enumerated(EnumType.STRING)
    private PlanVehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    public void updateHits(Long hits) {
        this.hits = hits;
    }

    public void modifyPlan(String title, String startDate, String endDate,
                           Long adultCount, Long childCount, String vehicle
    ){
        this.title = title;
        this.startDate = LocalDate.parse(startDate);
        this.endDate = LocalDate.parse(endDate);
        this.adultCount = adultCount;
        this.childCount = childCount;
        this.vehicle = PlanVehicle.valueOf(vehicle);
    }
}
