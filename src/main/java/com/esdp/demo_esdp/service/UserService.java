package com.esdp.demo_esdp.service;

import com.esdp.demo_esdp.dto.UserResponseDTO;
import com.esdp.demo_esdp.entity.User;
import com.esdp.demo_esdp.exeption.UserAlreadyRegisteredException;
import com.esdp.demo_esdp.exeption.UserNotFoundException;
import com.esdp.demo_esdp.exeption.UserRegisterForm;
import com.esdp.demo_esdp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserResponseDTO register(UserRegisterForm form) {
        if (userRepository.existsByEmail(form.getEmail())) {
            throw new UserAlreadyRegisteredException();
        }

        var user = User.builder()
                .email(form.getEmail())
                .name(form.getName())
                .lastname(form.getLastName())
                .telNumber(form.getTelNumber())
                .password(encoder.encode(form.getPassword()))
                .build();

        userRepository.save(user);

        return UserResponseDTO.from(user);
    }

    public UserResponseDTO getByEmail(String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        return UserResponseDTO.from(user);
    }
}
