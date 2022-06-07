package com.esdp.demo_esdp.repositories;

import com.esdp.demo_esdp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
