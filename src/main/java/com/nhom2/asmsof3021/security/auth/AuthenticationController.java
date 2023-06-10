package com.nhom2.asmsof3021.security.auth;

import com.nhom2.asmsof3021.model.User;
import com.nhom2.asmsof3021.security.enums.Role;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    @GetMapping("/register")
    public String getRegister() {

        return "RegisterPage";
    }
    @PostMapping("/perform/register")
    @ResponseBody
    public ResponseEntity<RegisterResponse> register(@Validated @RequestBody RegisterRequest request) {
        User user = service.register(request);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }else {

            return ResponseEntity.ok(RegisterResponse.builder().username(user.getUsername()).email(user.getEmail()).build());
        }
    }

}
