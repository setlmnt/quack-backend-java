package com.backend.quack.dto.user;

import com.backend.quack.domain.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserSignUpDTO(
        @NotBlank
        String username,

        @NotBlank
        @Email
        String email,
        @NotBlank
        String password
) {
    public static UserSignUpDTO fromEntity(User user) {
        return new UserSignUpDTO(
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
