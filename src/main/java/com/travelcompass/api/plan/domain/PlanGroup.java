package com.travelcompass.api.plan.domain;

import com.travelcompass.api.global.entity.BaseEntity;
import com.travelcompass.api.global.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanGroup extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @ManyToOne
    private Plan plan;

    private String inviteCode; // 초대를 위한 유니크한 코드
}
