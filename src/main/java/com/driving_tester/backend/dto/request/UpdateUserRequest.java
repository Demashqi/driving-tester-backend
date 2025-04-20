package com.driving_tester.backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {
  
    @Email(message = "Invalid email format")
    private String email;
    
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
}