package com.airplanesoft.dms.rest;

import com.airplanesoft.dms.dto.DevicePlatformDTO;
import com.airplanesoft.dms.service.DevicePlatformService;
import com.airplanesoft.dms.util.ToDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/device-platforms")
public class DevicePlatformController {

    @Autowired
    private DevicePlatformService devicePlatformService;

    @GetMapping(path = "")
    List<DevicePlatformDTO> getAll(){
        return devicePlatformService.findAll().stream().map(ToDTO::fromDevice).collect(Collectors.toList());
    }
}
