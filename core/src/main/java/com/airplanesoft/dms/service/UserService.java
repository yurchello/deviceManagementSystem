package com.airplanesoft.dms.service;

import com.airplanesoft.dms.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    List<User> findAll();

    Page<User> findAll(Pageable pageable);

    void save(User person);

    long count();

    User getById(Integer id);
}
