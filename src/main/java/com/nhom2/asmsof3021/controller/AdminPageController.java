package com.nhom2.asmsof3021.controller;

import com.nhom2.asmsof3021.model.Category;
import com.nhom2.asmsof3021.model.Product;
import com.nhom2.asmsof3021.repository.CategoryRepo;
import com.nhom2.asmsof3021.security.UserRepository;
import com.nhom2.asmsof3021.service.CategoryService;
import com.nhom2.asmsof3021.service.ProductService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

import static com.nhom2.asmsof3021.utils.AuthenticateUtil.checkIsAuthenticated;


@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminPageController {
    public record BreadcrumbLink(String breadName,String breadLink) {

    }
    private final ProductService productService;
    private final CategoryService categoryService;
    private final CategoryRepo categoryRepository;
    private final UserRepository userRepository;
    private final HttpSession session;
    @PersistenceContext
    private final EntityManager entityManager;
    @GetMapping("/home")
    public String getAdminHome(Principal principal,Model model){
        checkIsAuthenticated(principal,session,userRepository);
        return "admin/index";
    }

    @GetMapping(value = {"/product/management","/product"})
    public String getProductManagementPage(Model model,Principal principal){
        checkIsAuthenticated(principal,session,userRepository);
        List<Category> categoryList=categoryRepository.findAllByEntityClassNameExists();
        model.addAttribute("categories" ,categoryList);
        List<BreadcrumbLink> breadcrumbLinkList=new ArrayList<>();
        breadcrumbLinkList.add(new BreadcrumbLink("Product","/admin/product"));
        breadcrumbLinkList.add(new BreadcrumbLink("Product Management","/admin/product/management"));
        session.setAttribute("breadcrumbs", breadcrumbLinkList);
        List<Product> list = productService.findAll();
        model.addAttribute("product",list.get(0));
        model.addAttribute("products",list);
        return "admin/productManagement";
    }


}
