package com.airplanesoft.dms.repository;

import com.airplanesoft.dms.dto.DeviceState;
import com.airplanesoft.dms.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DeviceRepository extends JpaRepository<Device,Integer>, JpaSpecificationExecutor<Device> {
    @Modifying
    @Query("UPDATE Device d SET d.deviceState = :state WHERE d.id = :deviceId")
    void updateDeviceState(@Param("deviceId") Integer deviceId, @Param("state") DeviceState deviceState);
}
