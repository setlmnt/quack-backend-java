package com.backend.quack.dto.user;

import com.backend.quack.domain.entity.User;
import jakarta.validation.constraints.NotBlank;

public record UserLoginDTO(
        @NotBlank
        String username,
        @NotBlank
        String password
) {
    public static UserLoginDTO fromEntity(User user) {
        return new UserLoginDTO(
                user.getUsername(),
                user.getPassword()
        );
    }

    public User toEntity() {
        return new User(
                null,
                username,
                null,
                password,
                null,
                null
        );
    }
}
