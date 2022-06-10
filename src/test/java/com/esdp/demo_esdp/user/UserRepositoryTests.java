package com.esdp.demo_esdp.user;

import com.esdp.demo_esdp.entity.User;
import com.esdp.demo_esdp.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    // Test if there is a user with an id = 1L
    @Test
    public void testUserGetById() {
        User user = userRepository.findById(1L).get();
        assertThat(user).isNotNull();
    }

    @Test
    public void testUserGetByEmail() {
        User user = userRepository.findByEmail("petr@test.test").get();
        assertThat(user).isNotNull();
    }
}
