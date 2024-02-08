package com.travelcompass.api.location.service;

import com.travelcompass.api.location.domain.Location;
import com.travelcompass.api.location.domain.LocationLike;
import com.travelcompass.api.location.domain.LocationType;
import com.travelcompass.api.location.repository.LocationLikeRepository;
import com.travelcompass.api.location.repository.LocationRepository;
import com.travelcompass.api.oauth.domain.User;
import com.travelcompass.api.region.domain.Region;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LocationLikeService {
    private final LocationLikeRepository locationLikeRepository;
    private final LocationRepository locationRepository;

    // 좋아요 토글 및 좋아요 수 조회
    @Transactional
    public Location toggleLikeAndRetrieveCount(Location location, User user) {
        LocationLike existingLike = locationLikeRepository.findByUserAndLocation(user, location);

        if (existingLike != null) {
            // 이미 좋아요를 눌렀다면 좋아요 취소
            locationLikeRepository.delete(existingLike);
        } else {
            // 좋아요 누르기
            locationLikeRepository.save(LocationLike.builder().user(user).location(location).build());
        }

        // 좋아요 수 업데이트
        updateLikeCount(location);
        return locationRepository.save(location);
    }

    // 좋아요 취소 및 좋아요 수 조회
    @Transactional
    public Location cancelLikeAndRetrieveCount(Location location, User user) {
        LocationLike existingLike = locationLikeRepository.findByUserAndLocation(user, location);

        if (existingLike != null) {
            // 이미 좋아요를 눌렀다면 좋아요 취소
            locationLikeRepository.delete(existingLike);

            // 좋아요 수 업데이트
            updateLikeCount(location);
            return locationRepository.save(location);
        } else {
            // 취소할 좋아요가 없으면 그대로 반환
            return location;
        }
    }

    // 좋아요 수 조회
    public Long getLikeCount(Location location) {
        return locationLikeRepository.countAllByLocation(location);
    }

    // 좋아요 수 업데이트
    private void updateLikeCount(Location location) {
        Long likeCount = locationLikeRepository.countAllByLocation(location);
        location.updateLikeCount(likeCount);
    }

    // 특정 사용자가 좋아요한 장소 조회
    public List<Location> findLocationsByUser(User user, Region region) {
        return locationLikeRepository.findAllByUserAndRegion(user, region).stream()
                .map(LocationLike::getLocation)
                .toList();
    }

    // 특정 사용자가 특정 지역의 특정 타입의 장소를 좋아요한 장소 조회
    public List<Location> findLocationsByUserAndLocationType(User user, Region region, LocationType locationType) {
        return locationLikeRepository.findAllByUserAndRegionAndLocationType(user, region, locationType).stream()
                .map(LocationLike::getLocation)
                .toList();
    }
}
