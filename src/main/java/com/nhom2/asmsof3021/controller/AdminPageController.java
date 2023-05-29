package com.nhom2.asmsof3021.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPageController {
    @GetMapping("/admin/home")
    public String getAdminHome(){
        return "admin/AdminHome";
    }
}
