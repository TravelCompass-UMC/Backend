package com.travelcompass.api.location.service;

import com.travelcompass.api.location.converter.ReviewConverter;
import com.travelcompass.api.location.domain.Location;
import com.travelcompass.api.location.dto.ReviewDto;
import com.travelcompass.api.location.repository.ReviewRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional
    public void saveReviews(List<ReviewDto> reviewDtos, Location location) {
        for (ReviewDto reviewDto : reviewDtos) {
            reviewRepository.save(ReviewConverter.toReview(reviewDto, location));
        }
    }

}
