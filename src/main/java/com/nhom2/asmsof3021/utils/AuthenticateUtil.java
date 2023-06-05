package com.nhom2.asmsof3021.utils;

import com.nhom2.asmsof3021.security.User;
import com.nhom2.asmsof3021.security.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

import java.security.Principal;

public class AuthenticateUtil {
    public static void checkIsAuthenticated(Principal principal, HttpSession session, UserRepository userRepository){
        if(principal!=null){
            User user=userRepository.findByEmailOrUsername(principal.getName()).orElseThrow();
            session.setAttribute("user",user);
        }
    }
}
