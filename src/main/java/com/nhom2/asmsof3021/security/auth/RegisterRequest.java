package com.nhom2.asmsof3021.security.auth;


import com.nhom2.asmsof3021.security.enums.Role;
import com.nhom2.asmsof3021.validation.annotations.PasswordValueMatch;
import com.nhom2.asmsof3021.validation.annotations.UniqueEmail;
import com.nhom2.asmsof3021.validation.annotations.UsernameUnique;
import com.nhom2.asmsof3021.validation.annotations.ValidPassword;
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
@PasswordValueMatch.List({
        @PasswordValueMatch(
                field = "password",
                fieldMatch = "confirmPassword",
                message = "Không trùng password"
        )
})
public class RegisterRequest {
    @NotBlank(message = "Username đang để trống")
    @UsernameUnique
    private String username;
    @NotBlank(message = "Email đang để trống")
    @Email(message = "Email không đúng định dạng")
    @UniqueEmail
    private String email;
    @ValidPassword
    @NotBlank(message = "Password đang để trống")
    private String password;
    @ValidPassword
    @NotBlank(message = "Xác nhận password đang để trống")
    private String confirmPassword;

    private Role role;
}
