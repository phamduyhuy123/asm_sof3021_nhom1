package com.nhom2.asmsof3021.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.nhom2.asmsof3021.utils.Router.routeToPage;

@Controller

public class UserPagesController {
    @GetMapping("/user/cart")
    public ModelAndView getCartPage(){
        ModelAndView modelAndView=routeToPage("user/Cart");
        return modelAndView;
    }
    @GetMapping("/user/checkout")
    public ModelAndView getCheckOutPage(){
        ModelAndView modelAndView=routeToPage("user/CheckOut");
        return modelAndView;
    }
    @GetMapping("/user/product/{id}")
    public ModelAndView getProductDetail(@PathVariable Integer id){
        ModelAndView modelAndView=routeToPage("user/ProductDetail");
        return modelAndView;
    }
}
