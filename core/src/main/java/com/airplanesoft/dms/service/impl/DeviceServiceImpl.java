package com.airplanesoft.dms.service.impl;

import com.airplanesoft.dms.dto.DeviceState;
import com.airplanesoft.dms.entity.Device;
import com.airplanesoft.dms.repository.DeviceRepository;
import com.airplanesoft.dms.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public List<Device> findAll() {
        return deviceRepository.findAll();
    }

    @Override
    public Device save(Device device) {
        return deviceRepository.save(device);
    }

    @Override
    public Device getById(Integer id) {
        return deviceRepository.getOne(id);
    }

    @Override
    public void updateDeviceState(Integer deviceId, String deviceState) {
        if (deviceRepository.existsById(deviceId)){
            deviceRepository.updateDeviceState(deviceId, DeviceState.valueOf(deviceState));
        }
    }
}
