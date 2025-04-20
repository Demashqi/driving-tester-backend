package com.driving_tester.backend.service;


import com.driving_tester.backend.dto.request.LoginRequest;
import com.driving_tester.backend.dto.request.RegisterRequest;
import com.driving_tester.backend.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest registerRequest);
    AuthResponse login(LoginRequest loginRequest);
}