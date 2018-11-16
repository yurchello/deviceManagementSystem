package com.airplanesoft.dms.service;

import com.airplanesoft.dms.dto.DevicePlatformDTO;

import java.util.List;

public interface DevicePlatformService extends Service {
    List<DevicePlatformDTO> getAll();
    long count();
}
