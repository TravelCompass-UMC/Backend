package com.travelcompass.api.location.repository;

import com.travelcompass.api.location.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
