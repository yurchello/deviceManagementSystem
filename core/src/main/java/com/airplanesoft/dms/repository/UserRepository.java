package com.airplanesoft.dms.repository;

import com.airplanesoft.dms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User,Integer> , JpaSpecificationExecutor<User> {
}
