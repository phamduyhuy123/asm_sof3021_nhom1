package com.nhom2.asmsof3021.utils;

import com.nhom2.asmsof3021.model.User;
import com.nhom2.asmsof3021.repository.UserRepository;
import jakarta.servlet.http.HttpSession;

import java.security.Principal;

public class AuthenticateUtil {
    public static void checkIsAuthenticated(Principal principal, HttpSession session, UserRepository userRepository){
        if(principal!=null){
            User user=userRepository.findByEmail(principal.getName()).orElseThrow();
            session.setAttribute("user",user);
        }
    }
}
