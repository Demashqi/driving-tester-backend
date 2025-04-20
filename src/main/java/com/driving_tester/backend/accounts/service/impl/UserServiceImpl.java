package com.driving_tester.backend.accounts.service.impl;

import com.driving_tester.backend.accounts.dto.request.UpdateUserRequest;
import com.driving_tester.backend.accounts.dto.response.UserResponse;
import com.driving_tester.backend.accounts.exception.ResourceNotFoundException;
import com.driving_tester.backend.accounts.model.User;
import com.driving_tester.backend.accounts.repository.UserRepository;
import com.driving_tester.backend.accounts.service.UserService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    @Transactional
    public UserResponse updateUser(Long id, UpdateUserRequest updateRequest) { // Changed parameter
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        updateRequest.setPassword(passwordEncoder.encode(updateRequest.getPassword()));
        // Only update allowed fields
        modelMapper.map(updateRequest, user);
        User updatedUser = userRepository.save(user);
        
        return modelMapper.map(updatedUser, UserResponse.class);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        userRepository.delete(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}