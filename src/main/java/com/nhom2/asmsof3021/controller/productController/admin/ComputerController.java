package com.nhom2.asmsof3021.controller.productController.admin;

import com.nhom2.asmsof3021.controller.productController.CrudMvcMethod;
import com.nhom2.asmsof3021.model.Computer;
import com.nhom2.asmsof3021.model.Monitor;
import com.nhom2.asmsof3021.model.ProductListImage;
import com.nhom2.asmsof3021.repository.productRepo.ComputerRepo;
import com.nhom2.asmsof3021.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/product/management/Computer")
public class ComputerController extends CrudMvcMethod<Computer, Integer> {
    private final ComputerRepo repo;
    private final ProductService productService;

    @Override
    @PostMapping("/create")
    public String create(@ModelAttribute Computer computer, @RequestPart("images") MultipartFile[] images, @RequestPart("imagePhoto")MultipartFile photoImage, RedirectAttributes redirectAttributes){
        List<ProductListImage> getImages = productService.saveImage(computer,images,photoImage);
        computer.setProductListImages(getImages);
        repo.save(computer);
        return "redirect:/admin/product/management";
    }

    @Override
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Computer computer=repo.findById(id).orElseThrow();
        if(computer!=null){
            productService.deleteProduct(id);
        }
        return "redirect:/admin/product/management";
    }

    @Override
    @PostMapping("/update/{id}")
    public String update(Computer computer, @PathVariable Integer id,@RequestPart("images") MultipartFile[] images, @RequestPart("imagePhoto")MultipartFile photoImage, RedirectAttributes redirectAttributes) {
        if(repo.findById(id).isPresent()){
            List<ProductListImage> getImages = productService.saveImage(computer,images,photoImage);
            computer.setProductListImages(getImages);
            repo.save(computer);
            redirectAttributes.addFlashAttribute("statusMessage","Success");
        }
        return "redirect:/admin/product/management";
    }
}
