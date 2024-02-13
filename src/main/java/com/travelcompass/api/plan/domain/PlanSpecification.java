package com.travelcompass.api.plan.domain;

import com.travelcompass.api.global.api_payload.ErrorCode;
import com.travelcompass.api.global.exception.GeneralException;
import com.travelcompass.api.hashtag.domain.HashtagPlan;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PlanSpecification {

    public static Specification<Plan> filteredByParameters(Integer days, Long regionId, String vehicle, List<String> hashtags){
        return  (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (days != null && days >= 0) {
                predicates.add(criteriaBuilder.equal(root.get("days"), days));
            } else if (days != null && days < 0) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("days"), 2));
            }
            if (regionId != null) {
                predicates.add(criteriaBuilder.equal(root.get("region").get("id"), regionId));
            }
            if (vehicle != null) {
                try {
                    PlanVehicle planVehicle = PlanVehicle.valueOf(vehicle);
                    predicates.add(criteriaBuilder.equal(root.get("vehicle"), planVehicle));
                } catch (IllegalArgumentException e) {
                    throw GeneralException.of(ErrorCode.WRONG_VEHICLE_PARAM);
                }
            }
            if (hashtags != null && !hashtags.isEmpty()) {
                Join<Plan, HashtagPlan> hashtagJoin = root.join("hashtagPlans");
                predicates.add(hashtagJoin.get("hashtag").get("name").in(hashtags));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
