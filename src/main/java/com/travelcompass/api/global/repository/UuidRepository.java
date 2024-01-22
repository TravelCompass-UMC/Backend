package com.travelcompass.api.global.repository;

import com.travelcompass.api.global.entity.Uuid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UuidRepository extends JpaRepository<Uuid, Long> {

}
