package com.airplanesoft.dms.service;

import com.airplanesoft.dms.entity.JobPosition;
import com.airplanesoft.dms.entity.User;

import java.util.List;
import java.util.Set;

public interface JobPositionService {
    List<JobPosition> findAll();
}
