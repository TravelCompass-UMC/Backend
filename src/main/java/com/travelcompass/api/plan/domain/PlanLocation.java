package com.travelcompass.api.plan.domain;

import com.travelcompass.api.global.entity.BaseEntity;
import com.travelcompass.api.location.domain.Location;
import com.travelcompass.api.region.domain.Region;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanLocation extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalTime arrival;

    @ManyToOne(fetch = FetchType.LAZY)
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    private  Plan plan;

    private Long travelDay;
}
