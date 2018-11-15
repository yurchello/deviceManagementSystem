package com.airplanesoft.dms.rest;

import com.airplanesoft.dms.entity.Device;
import com.airplanesoft.dms.entity.DevicePlatform;
import com.airplanesoft.dms.entity.DeviceState;
import com.airplanesoft.dms.service.DevicePlatformService;
import com.airplanesoft.dms.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    private DevicePlatformService devicePlatformService;

    @Autowired
    private DeviceService deviceService;



    @GetMapping(path = "/test")
    void test(){
        Device device = new Device();

        List<DevicePlatform> platformList = devicePlatformService.findAll();
        Optional<DevicePlatform> devicePlatformOptional = devicePlatformService.findByName("android");

        device.setDevicePlatform(devicePlatformOptional.get());
        device.setDeviceState(DeviceState.INACTIVE);
        device.setCreated(ZonedDateTime.now());
        device.setModified(ZonedDateTime.now());

        List<Device> devices1 = deviceService.findAll();

        deviceService.save(device);

         List<Device> devices2 = deviceService.findAll();

        System.out.println();
    }
}
