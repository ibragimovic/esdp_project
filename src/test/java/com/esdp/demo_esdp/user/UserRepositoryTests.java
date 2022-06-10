package com.esdp.demo_esdp.user;

import com.esdp.demo_esdp.entity.User;
import com.esdp.demo_esdp.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    // Test if there is a user with an id = 1L
    @Test
    public void testUserGetById() {
        User user = userRepository.findById(1L).get();
        assertThat(user).isNotNull();
    }
}
