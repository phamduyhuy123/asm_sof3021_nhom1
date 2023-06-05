package com.nhom2.asmsof3021.controller.productController.admin;

import com.nhom2.asmsof3021.controller.productController.CrudMvcMethod;
import com.nhom2.asmsof3021.model.Laptop;
import com.nhom2.asmsof3021.model.Monitor;
import com.nhom2.asmsof3021.repository.productRepo.LaptopRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/product/management/Laptop")
public class LaptopController extends CrudMvcMethod<Laptop, Integer> {
    private final LaptopRepo repo;

    @Override
    @PostMapping("/create")
    @ResponseBody
    public String create(@ModelAttribute Laptop laptop, RedirectAttributes redirectAttributes){
        repo.save(laptop);
        return "/admin/product/management";
    }

    @Override
    @DeleteMapping("/DELETE/{id}")
    @ResponseBody
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Laptop laptop=repo.findById(id).orElseThrow();
        if(laptop!=null){
            repo.delete(laptop);
        }
        return "/admin/product/management";
    }

    @Override
    @PutMapping("/UPDATE/{id}")
    @ResponseBody
    public String update(Laptop laptop, @PathVariable Integer id, RedirectAttributes redirectAttributes) {
        if(repo.findById(id).isPresent()){
            repo.save(laptop);
            redirectAttributes.addFlashAttribute("statusMessage","Success");
        }
        return "/admin/product/management";
    }
}
