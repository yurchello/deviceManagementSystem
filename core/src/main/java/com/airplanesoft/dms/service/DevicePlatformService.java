package com.airplanesoft.dms.service;

import com.airplanesoft.dms.entity.DevicePlatform;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface DevicePlatformService {
    List<DevicePlatform> findAll();
    Optional<DevicePlatform> findByName(String name);
}
