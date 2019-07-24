package com.airplanesoft.dms.service;

import com.airplanesoft.dms.entity.Device;
import com.airplanesoft.dms.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Set;

public interface UserService {
    List<User> findAll();

    Page<User> findAll(Pageable pageable);

    User save(User person);

    long count();

    User getById(Integer id);

    Set<Device> getDevicesByUserId(Integer userId);

    User addDevice(Integer userId, Device device);

    User removeDevice(Integer userId,Integer deviceId);

    User updateJobPositions(Integer id, List<String> jobPositions);

    Integer delete(Integer id);
}
