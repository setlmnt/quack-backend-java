package com.backend.quack.controller;

import com.backend.quack.domain.entity.User;
import com.backend.quack.dto.TokenDTO;
import com.backend.quack.dto.user.UserPostDTO;
import com.backend.quack.dto.user.UserResponseDTO;
import com.backend.quack.service.TokenService;
import com.backend.quack.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserService userService;

    @PostMapping("/login")
    public TokenDTO authenticate(
            @RequestBody @Valid UserPostDTO userPostDTO
    ) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userPostDTO.username(),
                userPostDTO.password()
        );
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        String tokenJwt = tokenService.generateToken((User) authentication.getPrincipal());
        return new TokenDTO(tokenJwt);
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(
            @RequestBody @Valid UserPostDTO userPostDTO
    ) {
        userService.create(userPostDTO);
    }
}
