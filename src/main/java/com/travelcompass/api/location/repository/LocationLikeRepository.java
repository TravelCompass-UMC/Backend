package com.travelcompass.api.location.repository;

import com.travelcompass.api.location.domain.Location;
import com.travelcompass.api.location.domain.LocationLike;
import com.travelcompass.api.oauth.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationLikeRepository extends JpaRepository<LocationLike, Long> {
    Long countAllByLocation(Location location);

    // 사용자와 장소에 대한 좋아요 정보 조회
    LocationLike findByUserAndLocation(User user, Location location);

    // 특정 사용자의 좋아요한 LocationLike 목록 조회
    List<LocationLike> findAllByUser(User user);
}
