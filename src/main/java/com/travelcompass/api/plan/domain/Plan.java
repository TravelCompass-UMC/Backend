package com.travelcompass.api.plan.domain;

import com.travelcompass.api.global.entity.BaseEntity;
import com.travelcompass.api.region.domain.Region;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Plan extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private PlanVehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    private Region region;

}
