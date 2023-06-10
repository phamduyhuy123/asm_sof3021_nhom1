package com.nhom2.asmsof3021.security.auth;

import com.nhom2.asmsof3021.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
@Component
@RequiredArgsConstructor
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    private final HttpSession httpSession;
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        List<Product> products;
        Optional<List<Product>> optionalProducts = Optional.ofNullable((List<Product>) httpSession.getAttribute("cartItems"));
        if (optionalProducts.isPresent()) {
            products = optionalProducts.get();
            HttpSession session = request.getSession(false);
            session.setAttribute("cartItems", products);
        }
        response.sendRedirect("/login");
    }
}
