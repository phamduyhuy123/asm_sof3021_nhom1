package com.nhom2.asmsof3021.security.auth;

import com.nhom2.asmsof3021.security.User;
import com.nhom2.asmsof3021.security.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.NoSuchElementException;



@Controller
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    @GetMapping("/register")
    public ModelAndView getRegister() {
        ModelAndView modelAndView = new ModelAndView("RegisterPage");
        RegisterRequest request=new RegisterRequest();

        modelAndView.addObject("registerUser",request);
        return modelAndView;
    }
    @PostMapping("/perform_register")
    public ModelAndView register(@Validated RegisterRequest request) {
        User response = service.register(request);
        if (response == null) {
            return new ModelAndView("redirect:/register");
        }
        return new ModelAndView("redirect:/home");
    }

//    @PostMapping("/authenticate")
//    public void authenticate(
//             @ModelAttribute("loginUser") AuthenticationRequest request,
//            RedirectAttributes redirectAttributes
//    ) {
//
//    }



}
