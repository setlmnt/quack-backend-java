package com.backend.quack.dto.user;

import com.backend.quack.domain.entity.User;
import jakarta.validation.constraints.NotBlank;

public record UserResponseDTO(
        Long id,
        String username,
        String password
) {
    public static UserResponseDTO fromEntity(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getPassword()
        );
    }

    public User toEntity() {
        return new User(
                id,
                username,
                password,
                null,
                null
        );
    }
}
