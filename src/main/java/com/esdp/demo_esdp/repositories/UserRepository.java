package com.esdp.demo_esdp.repositories;


import com.esdp.demo_esdp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "update users  set name = :name," +
            "lastname = :lastname," +
            "email = :email," +
            "tel_number = :telNumber," +
            "login = :login where id = :id", nativeQuery = true)
    void updateUserData(@Param("name") String name,
                        @Param("lastname") String lastname,
                        @Param("email") String email,
                        @Param("telNumber") String telNumber,
                        @Param("login") String login,
                        @Param("id") Long id);
}