package com.airplanesoft.dms.rest;

import com.airplanesoft.dms.dto.UserDto;
import com.airplanesoft.dms.entity.JobPosition;
import com.airplanesoft.dms.entity.User;
import com.airplanesoft.dms.service.JobPositionService;
import com.airplanesoft.dms.service.UserService;
import com.airplanesoft.dms.util.ToDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.data.domain.Pageable;


@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JobPositionService jobPositionService;

    @GetMapping(path = "")
    public List<UserDto> getAll(UserDto userDto, Pageable pageable){
        Page<User> page = userService.findAll(pageable);
        return page.getContent().stream()
                .map(ToDTO::fromUser)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/count")
    public Long count(){
        return userService.count();
    }

    @GetMapping(path = "/{id}")
    public UserDto getUser(@PathVariable Integer id){
        return ToDTO.fromUser(userService.getById(id));
    }


    @GetMapping(path = "/email/{email}")
    public List<User> getUser(@PathVariable String email){
        return userService.findAll();
    }

    @GetMapping(path = "/create")
    public void createUser(){
        User user = new User();
        user.setFirstName("eee");
        user.setLastName("dfsdfd");
        user.setEmail("dsfsdfds" + user.hashCode());
        user.setCreated(ZonedDateTime.now());
        user.setModified(ZonedDateTime.now());

        Set<JobPosition> positions = new HashSet<>(jobPositionService.findAll());
        JobPosition jobPosition = new JobPosition();

        user.setJobPositions(positions);

        userService.save(user);
    }

}
