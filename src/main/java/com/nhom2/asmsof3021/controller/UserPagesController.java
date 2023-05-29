package com.nhom2.asmsof3021.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller

public class UserPagesController {
    @GetMapping("/cart")
    public ModelAndView getCartPage(){
        ModelAndView modelAndView=new ModelAndView("user/Cart");
        return modelAndView;
    }
    @GetMapping("/user/checkout")
    public ModelAndView getCheckOutPage(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("is authenticated "+auth.isAuthenticated());
        ModelAndView modelAndView=new ModelAndView("user/CheckOut");
        return modelAndView;
    }
    @GetMapping("/user/product/{id}")
    public ModelAndView getProductDetail(@PathVariable Integer id){
        ModelAndView modelAndView=new ModelAndView("user/ProductDetail");
        return modelAndView;
    }
}
