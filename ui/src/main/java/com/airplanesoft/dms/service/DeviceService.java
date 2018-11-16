package com.airplanesoft.dms.service;

import com.airplanesoft.dms.dto.DeviceDto;
import com.airplanesoft.dms.dto.UserDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

//import org.springframework.data.domain.Pageable;


public interface DeviceService extends Service{
    DeviceDto getById(Integer id);
    long count();
    long count(DeviceDto criteria);
    List<DeviceDto> findAll(Pageable pageable);
    List<DeviceDto> findAll(DeviceDto criteria, Pageable pageable);
    Set<DeviceDto> getDevicesByUserId(Integer userId);
    void save(DeviceDto deviceDto);
}
