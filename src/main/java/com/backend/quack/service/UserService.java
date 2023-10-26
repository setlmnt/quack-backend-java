package com.backend.quack.service;

import com.backend.quack.domain.entity.User;
import com.backend.quack.domain.repository.UserRepository;
import com.backend.quack.dto.user.UserResponseDTO;
import com.backend.quack.dto.user.UserSignUpDTO;
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

    public void create(UserSignUpDTO signUpDTO) {
        User user = userRepository.findUserByUsername(signUpDTO.username());

        if (user != null) {
            throw new InvalidUserException("Invalid username");
        }

        user = signUpDTO.toEntity();
        user.setPassword(passwordEncoder.encode(signUpDTO.password()));

        UserResponseDTO.fromEntity(userRepository.save(user));
    }
}
