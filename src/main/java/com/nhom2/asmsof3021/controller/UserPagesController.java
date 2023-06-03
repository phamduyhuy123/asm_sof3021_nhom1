package com.nhom2.asmsof3021.controller;

import com.nhom2.asmsof3021.security.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;


@Controller
@RequestMapping("/user")
@PreAuthorize("hasRole('USER')")

public class UserPagesController {
    @GetMapping("/cart")
    public ModelAndView getCartPage(Authentication authentication){
        if(authentication!=null){
            User user=(User) authentication.getPrincipal();
            System.out.println(user.getEmail());
        }
        ModelAndView modelAndView=new ModelAndView("user/Cart");
        return modelAndView;
    }
    @GetMapping("/checkout")
    public ModelAndView getCheckOutPage(Authentication authentication){

        ModelAndView modelAndView=new ModelAndView("user/CheckOut");
        return modelAndView;
    }
//    @GetMapping("/product/{id}")
//    public ModelAndView getProductDetail(@PathVariable Integer id){
//        ModelAndView modelAndView=new ModelAndView("user/ProductDetail");
//        return modelAndView;
//    }
}
