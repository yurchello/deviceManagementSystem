package com.airplanesoft.dms.service;

import com.airplanesoft.dms.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    void save(User person);

    long count();
}
