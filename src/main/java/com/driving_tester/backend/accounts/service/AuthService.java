package com.driving_tester.backend.accounts.service;


import com.driving_tester.backend.accounts.dto.request.LoginRequest;
import com.driving_tester.backend.accounts.dto.request.RegisterRequest;
import com.driving_tester.backend.accounts.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest registerRequest);
    AuthResponse login(LoginRequest loginRequest);
}