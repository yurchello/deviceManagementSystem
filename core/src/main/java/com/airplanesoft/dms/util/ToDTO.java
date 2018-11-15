package com.airplanesoft.dms.util;

import com.airplanesoft.dms.dto.UserDto;
import com.airplanesoft.dms.entity.JobPosition;
import com.airplanesoft.dms.entity.User;

import java.util.stream.Collectors;

public class ToDTO {
    public static UserDto fromUser(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setJobPositions(user.getJobPositions().stream().map(JobPosition::getName).collect(Collectors.toSet()));
        return userDto;
    }
}
