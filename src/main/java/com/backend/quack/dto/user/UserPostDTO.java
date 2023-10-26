package com.backend.quack.dto.user;

import com.backend.quack.domain.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserPostDTO(
        @NotBlank
        String username,

        @NotBlank
        @Email
        String email,
        @NotBlank
        String password
) {
    public static UserPostDTO fromEntity(User user) {
        return new UserPostDTO(
                user.getUsername(),
                user.getEmail(),
                user.getPassword()
        );
    }

    public User toEntity() {
        return new User(
                null,
                username,
                email,
                password,
                null,
                null
        );
    }
}
