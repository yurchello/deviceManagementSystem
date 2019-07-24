package com.airplanesoft.dms.rest;

import com.airplanesoft.dms.dto.DeviceDto;
import com.airplanesoft.dms.dto.UserDto;
import com.airplanesoft.dms.entity.Device;
import com.airplanesoft.dms.entity.DevicePlatform;
import com.airplanesoft.dms.entity.JobPosition;
import com.airplanesoft.dms.entity.User;
import com.airplanesoft.dms.http.RestResponse;
import com.airplanesoft.dms.service.DevicePlatformService;
import com.airplanesoft.dms.service.JobPositionService;
import com.airplanesoft.dms.service.UserService;
import com.airplanesoft.dms.util.FromDTO;
import com.airplanesoft.dms.util.ToDTO;
import java.util.Collections;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("api/user")
public class UserController {

    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    UserService userService;

    @Autowired
    JobPositionService jobPositionService;

    @Autowired
    DevicePlatformService devicePlatformService;

    @GetMapping(path = "")
    public RestResponse<List<UserDto>> getAll(UserDto userDto, Pageable pageable) {
        logger.info("Get users by pages: page size=" + pageable.getPageSize() + " page number=" + pageable.getPageNumber());
        Page<User> page = userService.findAll(pageable);
        return new RestResponse<>(page.getContent().stream()
                .map(ToDTO::fromUser)
                .collect(Collectors.toList()));
    }

    @GetMapping(path = "/count")
    public RestResponse<Long> count() {
        return new RestResponse(userService.count());
    }

    @GetMapping(path = "/{id}")
    public RestResponse<UserDto> getUser(@PathVariable Integer id, HttpServletResponse response) {
        return new RestResponse<>(ToDTO.fromUser(userService.getById(id)));
    }

    @GetMapping(path = "/{id}/devices")
    public RestResponse<Set<DeviceDto>> getUserDevices(@PathVariable Integer id) {
        return new RestResponse<>(userService.getById(id).getDevices().stream().map(ToDTO::fromDevice).collect(Collectors.toSet()));
    }

    @PutMapping("/{id}/device")
    public void addDeviceToUser(@PathVariable Integer id, @RequestBody DeviceDto deviceDto) {
        Device device = FromDTO.fromDeviceDTO(deviceDto);
        device.setCreated(ZonedDateTime.now());
        device.setModified(ZonedDateTime.now());
        DevicePlatform devicePlatform = devicePlatformService.findByName(deviceDto.getDevicePlatform()).orElseThrow(RuntimeException::new);
        device.setDevicePlatform(devicePlatform);
        userService.addDevice(id, device);
    }

    @PostMapping("")
    public RestResponse<UserDto> saveUser(@RequestBody UserDto userDto) {
        User rr = userService.save(FromDTO.toUser(userDto));
        return new RestResponse<>(ToDTO.fromUser(rr));
    }

    @PutMapping("/{id}/job-position")
    public RestResponse<UserDto> updateUserJobPosition(@PathVariable Integer id, @RequestBody List<String> jobPositions) {
        return new RestResponse<>(ToDTO.fromUser(userService.updateJobPositions(id, jobPositions)));
    }

    @GetMapping("/{id}/devices/count")
    public RestResponse<Long> countDevices(@PathVariable Integer id) {
        return new RestResponse<>((long) userService.getById(id).getDevices().size());
    }

    @DeleteMapping("")
    public RestResponse<Integer> deleteUser(@PathVariable Integer id) {
        return new RestResponse<>(userService.delete(id));
    }

}
