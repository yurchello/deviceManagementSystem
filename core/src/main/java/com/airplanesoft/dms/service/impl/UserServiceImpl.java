package com.airplanesoft.dms.service.impl;

import com.airplanesoft.dms.entity.Device;
import com.airplanesoft.dms.entity.User;
import com.airplanesoft.dms.repository.UserRepository;
import com.airplanesoft.dms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

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
        User user = userRepository.getOne(userId);
        return user.getDevices();
    }


}
