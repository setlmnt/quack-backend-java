package com.backend.quack.service;

import com.backend.quack.domain.entity.User;
import com.backend.quack.domain.repository.UserRepository;
import com.backend.quack.dto.user.UserPostDTO;
import com.backend.quack.dto.user.UserResponseDTO;
import com.backend.quack.exception.InvalidUserException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO create(UserPostDTO userPostDTO) {
        User user = userRepository.findUserByUsername(userPostDTO.username());

        if (user != null) {
            throw new InvalidUserException("Invalid username");
        }

        user = userPostDTO.toEntity();
        user.setPassword(passwordEncoder.encode(userPostDTO.password()));

        return UserResponseDTO.fromEntity(userRepository.save(user));
    }
}
