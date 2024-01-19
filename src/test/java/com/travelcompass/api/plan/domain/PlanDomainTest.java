package com.travelcompass.api.plan.domain;


import com.travelcompass.api.global.entity.UserEntity;
import com.travelcompass.api.location.domain.Location;
import com.travelcompass.api.region.domain.Region;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

public class PlanDomainTest {

    @Test
    @DisplayName("Plan 생성 테스트")
    void createPlan(){
        //given
        //when
        Region region = Region.builder()
                .id(1L)
                .name("제주")
                .build();

        Plan plan = Plan.builder()
                .title("제주 여행")
                .startDate(LocalDate.parse("2024-01-01"))
                .endDate(LocalDate.parse("2024-01-03"))
                .vehicle(PlanVehicle.PUBLIC)
                .region(region)
                .build();

        //then
        assertThat(plan.getTitle()).isEqualTo("제주 여행");
        assertThat(plan).isInstanceOf(Plan.class);
    }

    @Test
    @DisplayName("Plan Group 생성 테스트")
    public void createPlanGroup(){
        //given
        //when
        Region region = Region.builder()
                .id(1L)
                .name("제주")
                .build();

        Plan plan = Plan.builder()
                .title("제주 여행")
                .startDate(LocalDate.parse("2024-01-01"))
                .endDate(LocalDate.parse("2024-01-03"))
                .vehicle(PlanVehicle.PUBLIC)
                .region(region)
                .build();

        UserEntity user = new UserEntity();

        PlanGroup planGroup = PlanGroup.builder()
                .id(1L).name("제주팟").inviteCode("abcd").plan(plan).user(user).build();
        //then
        assertThat(planGroup.getId()).isEqualTo(1L);
        assertThat(planGroup.getPlan()).isEqualTo(plan);
        assertThat(planGroup.getUser()).isEqualTo(user);
        assertThat(planGroup).isInstanceOf(PlanGroup.class);
    }

    @Test
    @DisplayName("Plan Location 생성 테스트")
    public void createPlanLocation(){
        //given
        //when
        Region region = Region.builder()
                .id(1L)
                .name("제주")
                .build();

        Plan plan = Plan.builder()
                .title("제주 여행")
                .startDate(LocalDate.parse("2024-01-01"))
                .endDate(LocalDate.parse("2024-01-03"))
                .vehicle(PlanVehicle.PUBLIC)
                .region(region)
                .build();

        Location location = new Location();

        PlanLocation planLocation = PlanLocation.builder()
                .id(1L)
                .location(location)
                .arrival(LocalTime.parse("12:00"))
                .travelDay(1L)
                .plan(plan)
                .build();

        //then
        assertThat(planLocation.getId()).isEqualTo(1L);
        assertThat(planLocation.getLocation()).isEqualTo(location);
        assertThat(planLocation.getPlan()).isEqualTo(plan);
        assertThat(planLocation).isInstanceOf(PlanLocation.class);

    }
}
