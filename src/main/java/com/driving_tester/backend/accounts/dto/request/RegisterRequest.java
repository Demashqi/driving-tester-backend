package com.driving_tester.backend.accounts.dto.request;
// Jakarta Validation (correct for Spring Boot 3.x)
import jakarta.validation.constraints.NotBlank;

import com.driving_tester.backend.accounts.model.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
 
    @NotBlank
    @Email
    private String email;
    
    @NotBlank
    @Size(min = 6)
    private String password;
    
    private Role role; // Will be ignored for public registration
}