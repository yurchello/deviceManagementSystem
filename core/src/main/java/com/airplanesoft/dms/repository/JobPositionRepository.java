package com.airplanesoft.dms.repository;

import com.airplanesoft.dms.entity.JobPosition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Set;

public interface JobPositionRepository extends JpaRepository<JobPosition,Integer> {
    Set<JobPosition> findByNameIn(Collection<String> names);
}
