package com.airplanesoft.dms.util;

import com.airplanesoft.dms.dto.DeviceDto;
import com.airplanesoft.dms.dto.DevicePlatformDTO;
import com.airplanesoft.dms.dto.JobPositionDto;
import com.airplanesoft.dms.dto.UserDto;
import com.airplanesoft.dms.entity.Device;
import com.airplanesoft.dms.entity.DevicePlatform;
import com.airplanesoft.dms.entity.JobPosition;
import com.airplanesoft.dms.entity.User;

import java.util.Objects;
import java.util.stream.Collectors;

public class ToDTO {
    public static UserDto fromUser(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        if(Objects.nonNull(user.getJobPositions())) {
            userDto.setJobPositions(user.getJobPositions().stream().map(JobPosition::getName).collect(Collectors.toSet()));
        }
        return userDto;
    }

    public static DeviceDto fromDevice(Device device) {
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setDevicePlatform(device.getDevicePlatform().getName());
        deviceDto.setDeviceState(device.getDeviceState().name());
        deviceDto.setId(device.getId());
        return deviceDto;
    }

    public static DevicePlatformDTO fromDevicePlatform(DevicePlatform devicePlatform) {
        DevicePlatformDTO dto = new DevicePlatformDTO();
        dto.setId(devicePlatform.getId());
        dto.setName(devicePlatform.getName());
        return dto;
    }

    public static JobPositionDto fromJobPosition(JobPosition jobPosition) {
        return new JobPositionDto(jobPosition.getId(), jobPosition.getName());
    }
}
