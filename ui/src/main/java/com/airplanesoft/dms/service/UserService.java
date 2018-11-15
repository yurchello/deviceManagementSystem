package com.airplanesoft.dms.service;

import com.airplanesoft.dms.dto.UserDto;
import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Pageable;


import java.util.List;


public interface UserService extends Service{
    UserDto getById(Integer id);
    UserDto getByEmail(String email);
    long count();
    long count(UserDto criteria);
    List<UserDto> findAll(Pageable pageable);
    List<UserDto> findAll(UserDto criteria, Pageable pageable);
}
