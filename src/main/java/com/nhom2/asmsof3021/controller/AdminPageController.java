package com.nhom2.asmsof3021.controller;

import com.nhom2.asmsof3021.model.Category;
import com.nhom2.asmsof3021.model.Product;
import com.nhom2.asmsof3021.repository.CategoryRepo;
import com.nhom2.asmsof3021.repository.productRepo.ProductRepo;
import com.nhom2.asmsof3021.repository.UserRepository;
import com.nhom2.asmsof3021.service.CategoryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    private final CategoryService categoryService;
    private final CategoryRepo categoryRepository;
    private final UserRepository userRepository;
    private final HttpSession session;
    private final ProductRepo productRepo;
    private final HttpServletRequest httpServletRequest;
    @GetMapping("/home")
    public String getAdminHome(Principal principal,Model model){
        checkIsAuthenticated(principal,session,userRepository);
        return "admin/index";
    }

    @GetMapping(value = {"/product/management","/product"})
    public String getProductManagementPage(Model model,Principal principal, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "7") int pageSize){
        checkIsAuthenticated(principal,session,userRepository);
        List<Product> list= productManagementDefault(model, categoryRepository, session, productRepo, page, pageSize);
        System.out.println("test: "+httpServletRequest.getAttribute("product"));
        if (httpServletRequest.getAttribute("product")==null){
            System.out.println("idonknow");
            model.addAttribute("product",list.get(0));

        }else{
            model.addAttribute("product",httpServletRequest.getAttribute("product"));
        }
        if (httpServletRequest.getAttribute("categoryViewName")== null ){
            model.addAttribute("categoryViewName","admin/product/"+list.get(0).getCategory().getEntityClassName());
        }else if(httpServletRequest.getAttribute("categoryViewName")!= null && httpServletRequest.getAttribute("categoryViewName").equals("khongtimthay")){
            model.addAttribute("categoryViewName", null);
        }else {

            model.addAttribute("categoryViewName",httpServletRequest.getAttribute("categoryViewName"));
        }


        return "admin/productManagement";
    }

    public static List<Product> productManagementDefault(Model model, CategoryRepo categoryRepository, HttpSession session, ProductRepo productRepo,
                                                         int page,  int pageSize) {
        List<Category> categoryList = categoryRepository.findAllByEntityClassNameExists();
        model.addAttribute("categories", categoryList);
        List<AdminPageController.BreadcrumbLink> breadcrumbLinkList = new ArrayList<>();
        breadcrumbLinkList.add(new AdminPageController.BreadcrumbLink("Product", "/admin/product"));
        breadcrumbLinkList.add(new AdminPageController.BreadcrumbLink("Product Management", "/admin/product/management"));
        session.setAttribute("breadcrumbs", breadcrumbLinkList);

//        if (page < 0) {
//            page = 0;
//        }
//
//        long totalProducts = productRepo.count(); // Tổng số lượng sản phẩm
//        int totalPages = (int) Math.ceil((double) totalProducts / pageSize); // Tính số lượng trang
//
//        if (page >= totalPages) {
//            page = 0;
//        }

        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        Page<Product> pages = productRepo.findAll(pageable);

        model.addAttribute("page", pages);
        System.out.println(pages.getTotalPages());
        // Lấy toàn bộ danh sách sản phẩm
        List<Product> allProducts = productRepo.findAll();
        model.addAttribute("products",allProducts);
        return allProducts;
    }


}
