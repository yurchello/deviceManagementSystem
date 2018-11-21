package com.airplanesoft.dms.service.impl;

import com.airplanesoft.dms.entity.Device;
import com.airplanesoft.dms.entity.User;
import com.airplanesoft.dms.repository.DeviceRepository;
import com.airplanesoft.dms.repository.UserRepository;
import com.airplanesoft.dms.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    UserRepository userRepository;
    @Autowired
    DeviceRepository deviceRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public long count(){
        return userRepository.count();
    }

    @Override
    public User getById(Integer id) {
        return userRepository.getOne(id);
    }

    @Override
    public Set<Device> getDevicesByUserId(Integer userId) {
        logger.info("Getting devices for User: id=" + userId);
        User user = userRepository.getOne(userId);
        return user.getDevices();
    }

    @Override
    public User addDevice(Integer userId, Device device){
        logger.info("Saving device: " + device + "for User: id=" + userId);
        Device savedDevice = deviceRepository.save(device);
        User user = userRepository.getOne(userId);
        user.getDevices().add(savedDevice);
        return userRepository.save(user);
    }

}
