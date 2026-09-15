package com.wardrobe.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignupRequest(
        @NotBlank String name,
        @Pattern(regexp = "^[a-zA-Z0-9_]{3,20}$",
                 message = "Username must be 3-20 characters: letters, numbers, underscore only")
        String username,
        @Email @NotBlank String email,
        @Size(min = 8, message = "Password must be at least 8 characters") String password
) {}
