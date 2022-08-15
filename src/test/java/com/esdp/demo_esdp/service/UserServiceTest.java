package com.esdp.demo_esdp.service;

import com.esdp.demo_esdp.dto.UserRegisterForm;
import com.esdp.demo_esdp.entity.User;
import com.esdp.demo_esdp.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private final long ID = 1L;

    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @Test
    void registerOAuth2User() {
    }

    @Test
    void register() {
        var form = mock(UserRegisterForm.class);
        when(form.getEmail()).thenReturn("test@test.kg");
        when(form.getLastName()).thenReturn("test_lastname");
        when(form.getLogin()).thenReturn("test_login");
        when(form.getName()).thenReturn("test_name");
        when(form.getPassword()).thenReturn("12345");
        when(form.getTelNumber()).thenReturn("+996000000000");
        var user = mock(User.class);
        when(user.getEmail()).thenReturn("test@test.kg");
        when(userRepository.existsByEmail("test@test.kg")).thenReturn(false);

        var userForm = userService.register(form);

        assertNotNull(userForm);
        verify(userRepository).existsByEmail(user.getEmail());
    }

    @Test
    void isUserExistByEmail() {
    }

    @Test
    void update() {
    }

    @Test
    void getByEmail() {
    }

    @Test
    void activateUser() {
    }

    @Test
    void getUsers() {
    }

    @Test
    void testGetUsers() {
    }

    @Test
    void getInactiveUsers() {
    }

    @Test
    void blockingUser() {
    }

    @Test
    void getUserByEmail() {
    }

    @Test
    void updateUserPassword() {
    }

    @Test
    void getEmailFromAuthentication() {
    }

    @Test
    void isUserEnabled() {
    }

    @Test
    void restorePassword() {
    }

    @Test
    void updatePassword() {
    }

    @Test
    void userNewPassword() {
    }

    @Test
    void userSaveTelephone() {
    }

    @Test
    void getUserName() {
    }

    @Test
    void getUsersEmail() {
    }

    @Test
    void getUserLogin() {
    }

    @Test
    void getUserLastName() {
    }

    @Test
    void getUserTel() {
    }

    @Test
    void getUserStatus() {
    }
}