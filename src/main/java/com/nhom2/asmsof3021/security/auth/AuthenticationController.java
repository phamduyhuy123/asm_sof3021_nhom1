package com.nhom2.asmsof3021.security.auth;

import com.nhom2.asmsof3021.security.enums.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

import static com.nhom2.asmsof3021.utils.Router.routeToPage;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @GetMapping("/register")
    public ModelAndView getRegister() {
        ModelAndView modelAndView = routeToPage("RegisterPage");
        RegisterRequest request=new RegisterRequest();
        request.setRole(Role.USER);
        modelAndView.addObject("registerUser",request);
        return modelAndView;
    }
    @PostMapping("/perform_register")
    public ModelAndView register(@Validated RegisterRequest request) {
        AuthenticationResponse response = service.register(request);
        if (response == null) {
            return new ModelAndView("redirect:/register");
        }
        return routeToPage("redirect:/home");
    }

    @PostMapping("/authenticate")
    public String authenticate(
             @ModelAttribute("loginUser") AuthenticationRequest request,
            RedirectAttributes redirectAttributes
    ) {
        System.out.println("sadsadsadsadsadsadsadsadsadsa "+request);
        try {
            AuthenticationResponse authenticationResponse = service.authenticate(request);
            redirectAttributes.addFlashAttribute("statusMessage", "Login success");
            redirectAttributes.addAttribute("token", authenticationResponse);


            return "redirect:/index";
        }catch (BadCredentialsException e){
            redirectAttributes.addFlashAttribute("statusMessage","Invalid username or password");
            return "redirect:/login?error=Invalid username or password";
        } catch (AuthenticationException e) {
            redirectAttributes.addFlashAttribute("statusMessage", e.getMessage());
            return "redirect:/login?error="+e.getLocalizedMessage();
        }
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }


}
