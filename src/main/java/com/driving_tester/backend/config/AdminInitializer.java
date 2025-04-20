package com.driving_tester.backend.config;

import com.driving_tester.backend.model.User;
import com.driving_tester.backend.model.Role;
import com.driving_tester.backend.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "app.admin")
public class AdminInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    private String email;
    private String password;
    
    // Set via configuration properties
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @PostConstruct
    public void initAdmin() {
        try {
            if (userRepository.findByEmail(email).isEmpty()) {
                User admin = User.builder()
                        .email(email)
                        .password(passwordEncoder.encode(password))
                        .role(Role.ADMIN)
                        .build();
                
                userRepository.save(admin);
                log.info("Admin account created successfully for {}", email);
            } else {
                log.info("Admin account already exists for {}", email);
            }
        } catch (Exception e) {
            log.error("Failed to initialize admin account: {}", e.getMessage());
            throw new IllegalStateException("Admin account initialization failed", e);
        }
    }
}