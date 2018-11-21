package com.airplanesoft.dms.util;

import com.airplanesoft.dms.dto.DeviceDto;
import com.airplanesoft.dms.dto.DeviceState;
import com.airplanesoft.dms.entity.Device;
import com.airplanesoft.dms.entity.DevicePlatform;

public class FromDTO {
    public  static Device fromDeviceDTO(DeviceDto deviceDto){
        Device device = new Device();
        device.setId(deviceDto.getId());
        device.setDeviceState(DeviceState.valueOf(deviceDto.getDeviceState()));
        device.setDevicePlatform(new DevicePlatform(deviceDto.getDevicePlatform()));
        return device;
    }
}
