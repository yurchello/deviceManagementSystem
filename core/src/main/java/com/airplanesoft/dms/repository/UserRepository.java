package com.airplanesoft.dms.repository;

import com.airplanesoft.dms.entity.Device;
import com.airplanesoft.dms.entity.JobPosition;
import com.airplanesoft.dms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Set;

public interface UserRepository extends JpaRepository<User,Integer> , JpaSpecificationExecutor<User> {
//    Set<Device> findDevicesByUserId(Integer id);

    List<User> findUsersByJobPositions(JobPosition jobPosition);
}
