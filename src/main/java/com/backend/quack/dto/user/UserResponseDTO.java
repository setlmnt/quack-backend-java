package com.backend.quack.dto.user;

import com.backend.quack.domain.entity.User;

public record UserResponseDTO(
        Long id,
        String username,
        String email,
        String password
) {
    public static UserResponseDTO fromEntity(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword()
        );
    }

    public User toEntity() {
        return new User(
                id,
                username,
                email,
                password,
                null,
                null
        );
    }
}
