package com.airplanesoft.dms.repository;

import com.airplanesoft.dms.entity.DevicePlatform;
import com.airplanesoft.dms.entity.JobPosition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DevicePlatformRepository extends JpaRepository<DevicePlatform,Integer> {
    Optional<DevicePlatform> findByName(String name);
}
