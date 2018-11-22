package com.airplanesoft.dms.service.impl;

import com.airplanesoft.dms.entity.DevicePlatform;
import com.airplanesoft.dms.repository.DevicePlatformRepository;
import com.airplanesoft.dms.service.DevicePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class DevicePlatformServiceImpl implements DevicePlatformService {

    @Autowired
    private DevicePlatformRepository devicePlatformRepository;

    @Override
    public List<DevicePlatform> findAll() {
        return devicePlatformRepository.findAll();
    }

    @Override
    public Optional<DevicePlatform> findByName(String name) {
        return devicePlatformRepository.findByName(name);
    }
}
