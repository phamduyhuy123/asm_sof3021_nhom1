package com.nhom2.asmsof3021.controller;

import com.nhom2.asmsof3021.model.Laptop;
import com.nhom2.asmsof3021.model.Product;
import com.nhom2.asmsof3021.repository.ProductRepo;
import com.nhom2.asmsof3021.security.auth.AuthenticationRequest;
import com.nhom2.asmsof3021.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.nhom2.asmsof3021.utils.Router.routeToPage;

@Controller
@RequiredArgsConstructor
public class RouteController {
    private final ProductService service;
//    @GetMapping("/category/{categoryId}")
//    public ResponseEntity<Product> getProductByCategoryId(@PathVariable Integer categoryId){
//
//    }
    @GetMapping({"/","/index","/home"})
    public ModelAndView getHomePage(){
        ModelAndView modelAndView=routeToPage("Home");
        List<Product> laptops= service.findProductCategoryById(1);
        modelAndView.addObject("laptops",laptops);


        return modelAndView;
    }
    @GetMapping("/login")
    public ModelAndView routeToLoginPage( @RequestParam(value = "error", required = false) String error){
        ModelAndView modelAndView=routeToPage("LoginPage");
        if(error!=null){
            return routeToPage("redirect:/login?error=failed");
        }
        modelAndView.addObject("loginUser",new AuthenticationRequest());


        return modelAndView;
    }



}
