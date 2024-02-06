package com.travelcompass.api.hashtag.repository;

import com.travelcompass.api.hashtag.domain.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    List<Hashtag> findByNameIn(List<String> name);
}
