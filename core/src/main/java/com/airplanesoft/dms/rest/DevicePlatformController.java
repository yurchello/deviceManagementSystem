package com.airplanesoft.dms.rest;

import com.airplanesoft.dms.dto.DevicePlatformDTO;
import com.airplanesoft.dms.http.RestResponse;
import com.airplanesoft.dms.service.DevicePlatformService;
import com.airplanesoft.dms.util.FromDTO;
import com.airplanesoft.dms.util.ToDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/device-platform")
public class DevicePlatformController {

    @Autowired
    private DevicePlatformService devicePlatformService;

    @GetMapping(path = "")
    RestResponse<List<DevicePlatformDTO>> getAll() {
        return new RestResponse<>(devicePlatformService.findAll().stream().map(ToDTO::fromDevicePlatform).collect(Collectors.toList()));
    }

    @PostMapping("")
    RestResponse<DevicePlatformDTO> createDevicePlatformDTO(@RequestBody DevicePlatformDTO devicePlatformDTO) {
        return new RestResponse<>(ToDTO.fromDevicePlatform(devicePlatformService.create(FromDTO.fromDevicePlatformDTO(devicePlatformDTO))));
    }

}
