package com.nhom2.asmsof3021.controller.productController.admin;

import com.nhom2.asmsof3021.model.Laptop;
import com.nhom2.asmsof3021.repository.productRepo.LaptopRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/product/management/Laptop")
public class LaptopController {
    private final LaptopRepo repo;
    @PostMapping("/create")
    public String create(@ModelAttribute Laptop laptop){

        return "admin/productManagement";
    }
}
