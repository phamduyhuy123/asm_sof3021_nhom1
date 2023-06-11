package com.nhom2.asmsof3021.controller;

import com.nhom2.asmsof3021.model.Product;
import com.nhom2.asmsof3021.model.ProductLine;
import com.nhom2.asmsof3021.repository.UserRepository;
import com.nhom2.asmsof3021.repository.productRepo.ProductRepo;
import com.nhom2.asmsof3021.service.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Array;
import java.security.Principal;
import java.util.*;

import static com.nhom2.asmsof3021.utils.AuthenticateUtil.checkIsAuthenticated;


@Controller
@RequiredArgsConstructor

public class RouteController {

//    @GetMapping("/category/{categoryId}")
//    public ResponseEntity<Product> getProductByCategoryId(@PathVariable Integer categoryId){
//
//    }
    private final ProductRepo repo;
    private final ProductService service;
    private final HttpSession session;
    private final UserRepository userRepository;
    @GetMapping({"/","/index"})
    public ModelAndView getHomePage(Principal principal){
        checkIsAuthenticated(principal,session,userRepository);

        ModelAndView modelAndView=new ModelAndView("index");
        List<Integer> categoryIds = Arrays.asList(1);
        List<Integer> brandIds = Arrays.asList(2,7); // Pass null for no filtering on brandIds
        List<Integer> productLineIds = new ArrayList<>();


        List<Product> products=service.findProductsByFilters(
                categoryIds,brandIds,productLineIds);
        for (Product p:products
             ) {
            System.out.println(p.getName());
        }
        List<Product> laptops= repo.findByCategory_CatalogId(1);
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

    @GetMapping("/cart")

    public ModelAndView getCartPage(){
        ModelAndView modelAndView=new ModelAndView("user/Cart");
        return modelAndView;
    }
    @GetMapping("/checkout")
    public ModelAndView getCheckOutPage(Authentication authentication){

        ModelAndView modelAndView=new ModelAndView("user/CheckOut");

        return modelAndView;
    }

    @GetMapping("/admin/login")
    public String goToLoginPage(String id,String ...object){
        return "admin/login";
    }
    @GetMapping("/filter")
    public String productCategory(
            Model model,
            @RequestParam(required = false, name = "categoryIds")List<Integer> cateId,
            @RequestParam(required = false, name = "brandIds")List<Integer> brandId,
            @RequestParam(required = false, name = "productLineIds")List<Integer> productLineId,
            @RequestParam(required = false, name = "minPrice")Integer minPrice,
            @RequestParam(required = false, name = "maxPrice")Integer maxPrice){
//        if (cateId == null) {
//            cateId = new ArrayList<>();
//        }
//        if (brandId == null) {
//            brandId = new ArrayList<>();
//        }
//        if (productLineId == null) {
//            productLineId = new ArrayList<>();
//        }
//        List<Product> products=service.findProductsByFilters(cateId,brandId,productLineId);
//        model.addAttribute("products",products);
        return "filter";
    }

}
