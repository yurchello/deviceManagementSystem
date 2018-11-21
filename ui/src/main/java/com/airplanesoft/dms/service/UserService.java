package com.airplanesoft.dms.service;

import com.airplanesoft.dms.dto.DeviceDto;
import com.airplanesoft.dms.dto.UserDto;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Set;


public interface UserService extends Service{
    UserDto getById(Integer id);
    UserDto getByEmail(String email);
    long count();
    long count(UserDto criteria);
    List<UserDto> findAll(Pageable pageable);
    List<UserDto> findAll(UserDto criteria, Pageable pageable);
    Set<DeviceDto> getDevicesByUserId(Integer userId, Pageable pageable);
    void saveDevice(UserDto userDto, DeviceDto deviceDto);
    long countDevices(Integer userId);

}
