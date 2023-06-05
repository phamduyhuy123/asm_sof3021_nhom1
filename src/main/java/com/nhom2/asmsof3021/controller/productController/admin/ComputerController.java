package com.nhom2.asmsof3021.controller.productController.admin;

import com.nhom2.asmsof3021.controller.productController.CrudMvcMethod;
import com.nhom2.asmsof3021.model.Computer;
import com.nhom2.asmsof3021.model.Monitor;
import com.nhom2.asmsof3021.repository.productRepo.ComputerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/product/management/Computer")
public class ComputerController extends CrudMvcMethod<Computer, Integer> {
    private final ComputerRepo repo;

    @Override
    @PostMapping("/create")
    @ResponseBody
    public String create(@ModelAttribute Computer computer, RedirectAttributes redirectAttributes){
        repo.save(computer);
        return "/admin/product/management";
    }

    @Override
    @DeleteMapping("/DELETE/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Computer computer=repo.findById(id).orElseThrow();
        if(computer!=null){
            repo.delete(computer);
        }
        return "redirect:/admin/productManagement";
    }

    @Override
    @PutMapping("/UPDATE/{id}")
    public String update(Computer computer, @PathVariable Integer id, RedirectAttributes redirectAttributes) {
        if(repo.findById(id).isPresent()){
            repo.save(computer);
            redirectAttributes.addFlashAttribute("statusMessage","Success");
        }
        return "redirect:/admin/productManagement";
    }
}
