package com.driving_tester.backend.service;

import com.driving_tester.backend.dto.request.UpdateUserRequest;
import com.driving_tester.backend.dto.response.UserResponse;

public interface UserService {
    UserResponse getUserById(Long id);
    UserResponse updateUser(Long id, UpdateUserRequest updateRequest); // Changed parameter type
    void deleteUser(Long id);
    boolean existsByEmail(String email);
}