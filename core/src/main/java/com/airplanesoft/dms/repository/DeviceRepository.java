package com.airplanesoft.dms.repository;

import com.airplanesoft.dms.entity.Device;
import com.airplanesoft.dms.entity.JobPosition;
import org.apache.tomcat.jni.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface DeviceRepository extends JpaRepository<Device,Integer> {
//    List<Device> findDevicesByUser(User user);
}
