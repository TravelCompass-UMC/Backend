package com.travelcompass.api.location.repository;

import com.travelcompass.api.location.domain.Location;
import com.travelcompass.api.location.domain.LocationLike;
import com.travelcompass.api.location.domain.LocationType;
import com.travelcompass.api.oauth.domain.User;
import com.travelcompass.api.region.domain.Region;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationLikeRepository extends JpaRepository<LocationLike, Long> {
    Long countAllByLocation(Location location);

    // 사용자와 장소에 대한 좋아요 정보 조회
    LocationLike findByUserAndLocation(User user, Location location);

    // 특정 사용자의 좋아요한 LocationLike 목록 조회
    @Query("select ll from LocationLike ll join ll.location l where ll.user = :user and l.region = :region")
    List<LocationLike> findAllByUserAndRegion(User user, Region region);

    // 특정 사용자의 특정 지역의 특정 장소타입에 대한 좋아요한 LocationLike 목록 조회
    @Query("select ll "
            + "from LocationLike ll join ll.location l join l.locationInfo li "
            + "where ll.user = :user and l.region = :region and li.locationType = :locationType")
    List<LocationLike> findAllByUserAndRegionAndLocationType(User user, Region region, LocationType locationType);
}
