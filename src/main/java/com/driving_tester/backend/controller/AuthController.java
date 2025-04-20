package com.driving_tester.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.driving_tester.backend.dto.request.LoginRequest;
import com.driving_tester.backend.dto.request.RegisterRequest;
import com.driving_tester.backend.dto.response.AuthResponse;
import com.driving_tester.backend.exception.AccessDeniedException;
import com.driving_tester.backend.model.Role;
import com.driving_tester.backend.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        // Only allow ADMIN to set ADMIN role
        if (request.getRole() == Role.ADMIN) {
            throw new AccessDeniedException("Only admins can create admin accounts");
        }
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}