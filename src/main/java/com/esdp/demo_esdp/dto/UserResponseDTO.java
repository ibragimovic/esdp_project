package com.esdp.demo_esdp.dto;

import com.esdp.demo_esdp.entity.User;
import lombok.*;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PACKAGE)
public class UserResponseDTO {
    private Long id;
    private String name;
    private String lastname;
    private String login;
    private String email;
    private String telNumber;
    private boolean enabled;

    public static UserResponseDTO from(User user) {
        return builder()
                .id(user.getId())
                .name(user.getName())
                .lastname(user.getLastname())
                .login(user.getLogin())
                .email(user.getEmail())
                .telNumber(user.getTelNumber())
                .enabled(user.isEnabled())
                .build();
    }
}
