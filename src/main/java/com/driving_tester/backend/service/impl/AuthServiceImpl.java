package com.driving_tester.backend.service.impl;

import com.driving_tester.backend.dto.request.LoginRequest;
import com.driving_tester.backend.dto.request.RegisterRequest;
import com.driving_tester.backend.dto.response.AuthResponse;
import com.driving_tester.backend.dto.response.UserResponse;
import com.driving_tester.backend.exception.ResourceNotFoundException;
import com.driving_tester.backend.model.Role;
import com.driving_tester.backend.model.User;
import com.driving_tester.backend.repository.UserRepository;
import com.driving_tester.backend.security.JwtTokenProvider;
import com.driving_tester.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    @Override
    public AuthResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        User user = User.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(registerRequest.getRole() != null ? 
                    registerRequest.getRole() : Role.STUDENT)
                .build();

        User savedUser = userRepository.save(user);
        String jwt = jwtTokenProvider.generateToken(savedUser);

        return AuthResponse.builder()
                .accessToken(jwt)
                .user(modelMapper.map(savedUser, UserResponse.class))
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", loginRequest.getEmail()));

        String jwt = jwtTokenProvider.generateToken(user);
        return AuthResponse.builder()
                .accessToken(jwt)
                .user(modelMapper.map(user, UserResponse.class))
                .build();
    }
}