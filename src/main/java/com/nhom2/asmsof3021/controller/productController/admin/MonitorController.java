package com.nhom2.asmsof3021.controller.productController.admin;

import com.nhom2.asmsof3021.controller.productController.CrudMvcMethod;
import com.nhom2.asmsof3021.model.Monitor;
import com.nhom2.asmsof3021.repository.productRepo.MonitorRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/product/management/Monitor")
public class MonitorController extends CrudMvcMethod<Monitor,Integer> {
    private final MonitorRepo repo;
    @Override
    @PostMapping("/create")
    @ResponseBody
    public String create(@ModelAttribute Monitor monitor, RedirectAttributes redirectAttributes){
        repo.save(monitor);
        return "/admin/product/management";
    }

    @Override
    @DeleteMapping("/DELETE/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Monitor monitor=repo.findById(id).orElseThrow();
        if(monitor!=null){
            repo.delete(monitor);
        }
        return "redirect:/admin/productManagement";
    }

    @Override
    @PutMapping("/UPDATE/{id}")
    public String update(Monitor product, @PathVariable Integer id, RedirectAttributes redirectAttributes) {
        if(repo.findById(id).isPresent()){
            repo.save(product);
            redirectAttributes.addFlashAttribute("statusMessage","Success");
        }
        return "redirect:/admin/productManagement";
    }


}
