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


public class UserPagesController {

//    @GetMapping("/product/{id}")
//    public ModelAndView getProductDetail(@PathVariable Integer id){
//        ModelAndView modelAndView=new ModelAndView("user/ProductDetail");
//        return modelAndView;
//    }
}
