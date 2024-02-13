package com.travelcompass.api.plan.domain;

import com.travelcompass.api.global.entity.BaseEntity;
import com.travelcompass.api.hashtag.domain.HashtagPlan;
import com.travelcompass.api.region.domain.Region;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
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

    private Long likeCount; // 좋아요 수

    private Long days; // 총 일수, 당일치기는 0

    @Enumerated(EnumType.STRING)
    private PlanVehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    @OneToMany(mappedBy = "plan")
    private List<HashtagPlan> hashtagPlans;

    public void updateHits(Long hits) {
        this.hits = hits;
    }

    public void updateLikeCount(Long likeCount) { this.likeCount = likeCount; }

    public void modifyPlan(String title, LocalDate startDate, LocalDate endDate,
                           Long adultCount, Long childCount, String vehicle
    ){
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.adultCount = adultCount;
        this.childCount = childCount;
        this.vehicle = PlanVehicle.valueOf(vehicle);
    }
}
