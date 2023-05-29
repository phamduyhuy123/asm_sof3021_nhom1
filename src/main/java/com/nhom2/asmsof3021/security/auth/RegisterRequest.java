package com.nhom2.asmsof3021.security.auth;


import com.nhom2.asmsof3021.security.enums.Role;
import jakarta.validation.constraints.Email;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RegisterRequest {
    @NotBlank(message = "Username đang để trống")

    private String username;
    @NotBlank
    @Email
    private String email;


    private String password;
    @NotBlank
    private String confirmPassword;

    private Role role;
}
