package com.travelcompass.api.location.converter;

import static ch.qos.logback.classic.spi.ThrowableProxyVO.build;

import com.travelcompass.api.location.domain.Location;
import com.travelcompass.api.location.domain.Review;
import com.travelcompass.api.location.dto.ReviewDto;
import java.time.LocalDate;

public class ReviewConverter {

    private ReviewConverter() {
    }

    public static Review toReview(ReviewDto reviewDto, Location location) {
        LocalDate reviewDate = convertToLocalDate(reviewDto.getReviewDate());

        return Review.builder()
                .username(reviewDto.getUsername())
                .content(reviewDto.getContent())
                .reviewDate(reviewDate)
                .location(location)
                .build();
    }

    // "23.12.31.일" 형태인 경우 23년 12월 31일로 LocalDate로 변환
    // "1.1.월" 형태인 경우 현재의 년도 + 1월 1일로 LocalDate로 변환
    private static LocalDate convertToLocalDate(String visitDate) {
        String[] date = visitDate.split("\\.");
        if (date.length == 4) {
            return LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]),
                    Integer.parseInt(date[2]));
        } else {
            return LocalDate.of(LocalDate.now().getYear(), Integer.parseInt(date[0]),
                    Integer.parseInt(date[1]));
        }
    }

}
