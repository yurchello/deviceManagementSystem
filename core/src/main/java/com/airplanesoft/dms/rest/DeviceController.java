package com.airplanesoft.dms.rest;

import com.airplanesoft.dms.dto.DeviceDto;
import com.airplanesoft.dms.http.RestResponse;
import com.airplanesoft.dms.service.DevicePlatformService;
import com.airplanesoft.dms.service.DeviceService;
import com.airplanesoft.dms.util.ToDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/device")
public class DeviceController {

    @Autowired
    private DevicePlatformService devicePlatformService;

    @Autowired
    private DeviceService deviceService;

    @GetMapping(path = "/{id}")
    public RestResponse<DeviceDto> getDevice(@PathVariable Integer id) {
        return new RestResponse<>(ToDTO.fromDevice(deviceService.getById(id)));
    }

    @PutMapping("{id}/state/{name}")
    public void changeDeviceState(@PathVariable Integer id, @PathVariable String name) {
        deviceService.updateDeviceState(id, name);
    }
}
