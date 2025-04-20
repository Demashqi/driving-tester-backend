package com.driving_tester.backend.accounts.service;

import com.driving_tester.backend.accounts.dto.request.UpdateUserRequest;
import com.driving_tester.backend.accounts.dto.response.UserResponse;

public interface UserService {
    UserResponse getUserById(Long id);
    UserResponse updateUser(Long id, UpdateUserRequest updateRequest); // Changed parameter type
    void deleteUser(Long id);
    boolean existsByEmail(String email);
}