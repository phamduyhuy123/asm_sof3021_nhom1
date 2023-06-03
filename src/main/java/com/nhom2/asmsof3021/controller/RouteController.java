package com.nhom2.asmsof3021.controller;

import com.nhom2.asmsof3021.model.Laptop;
import com.nhom2.asmsof3021.model.Product;
import com.nhom2.asmsof3021.repository.ProductRepo;
import com.nhom2.asmsof3021.security.User;
import com.nhom2.asmsof3021.security.auth.AuthenticationRequest;
import com.nhom2.asmsof3021.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;



@Controller
@RequiredArgsConstructor

public class RouteController {
    private final ProductService service;
//    @GetMapping("/category/{categoryId}")
//    public ResponseEntity<Product> getProductByCategoryId(@PathVariable Integer categoryId){
//
//    }
    @GetMapping("/")
    public ModelAndView getHomePage(Authentication authentication){
        ModelAndView modelAndView=new ModelAndView("index");
        if(authentication!=null){
            modelAndView.addObject("isAuthenticated",authentication.isAuthenticated());
        }

        List<Product> laptops= service.findProductCategoryById(1);
        modelAndView.addObject("laptops",laptops);


        return modelAndView;
    }
    @GetMapping("/login")
    public ModelAndView routeToLoginPage( @RequestParam(value = "error", required = false) String error){
        ModelAndView modelAndView=new ModelAndView("LoginPage");
        if(error!=null){
            modelAndView.addObject("msg",error);
            return modelAndView;
        }



        return modelAndView;
    }

    @GetMapping("/admin/login")
    public String goToLoginPage(){
        return "admin/login";
    }

}
