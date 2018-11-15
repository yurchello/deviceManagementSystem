package com.airplanesoft.dms.service.impl;

import com.airplanesoft.dms.entity.User;
import com.airplanesoft.dms.repository.UserRepository;
import com.airplanesoft.dms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public long count(){
        return userRepository.count();
    }
}
