package com.airplanesoft.dms.repository;

import com.airplanesoft.dms.entity.Device;
import com.airplanesoft.dms.entity.JobPosition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device,Integer> {
}
