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
    private String lastName;
    private String email;

    public static UserResponseDTO from(User user) {
        return builder()
                .id(user.getId())
                .name(user.getName())
                .lastName(user.getLastname())
                .email(user.getEmail())
                .build();
    }
}
