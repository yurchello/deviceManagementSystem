package com.airplanesoft.dms.service.impl;

import com.airplanesoft.dms.entity.JobPosition;
import com.airplanesoft.dms.repository.JobPositionRepository;
import com.airplanesoft.dms.service.JobPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Transactional
@Service
public class JobPositionServiceImpl implements JobPositionService {

    @Autowired
    private JobPositionRepository jobPositionRepository;

    @Override
    public List<JobPosition> findAll() {
        return jobPositionRepository.findAll();
    }

    @Override
    public JobPosition getByName(String name) {
        return jobPositionRepository.findOne(Example.of(new JobPosition(name))).orElse(null);
    }
}
