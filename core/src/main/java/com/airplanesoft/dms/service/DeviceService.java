package com.airplanesoft.dms.service;

import com.airplanesoft.dms.dto.DeviceState;
import com.airplanesoft.dms.entity.Device;

import java.util.List;

public interface DeviceService {
    List<Device> findAll();
    Device save(Device device);
    Device getById(Integer id);
    Device updateDeviceState(Integer deviceId, String deviceState);
}
