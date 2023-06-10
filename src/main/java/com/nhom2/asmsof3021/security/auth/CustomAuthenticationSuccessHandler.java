package com.nhom2.asmsof3021.security.auth;

import com.nhom2.asmsof3021.model.Product;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final HttpSession httpSession;

    public CustomAuthenticationSuccessHandler(HttpSession httpSession) {
        this.httpSession = httpSession;
    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        List<Product> products;
        Optional<List<Product>> optionalProducts = Optional.ofNullable((List<Product>) httpSession.getAttribute("cartItems"));
        if (optionalProducts.isPresent()) {
            products = optionalProducts.get();
            HttpSession session = request.getSession(true);
            session.setAttribute("cartItems", products);
        }
//        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println(authentication.getPrincipal());
        response.sendRedirect("/index");
    }
}
