package com.java.movieticketingsystem.email.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
public class ForgotPasswordRequest {
    @NotBlank(message = "Email is required and cannot be empty")
    @Email(message = "Invalid email format")
    private String email;
}
