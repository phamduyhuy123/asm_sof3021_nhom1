package com.nhom2.asmsof3021.controller.productController.admin;

import com.nhom2.asmsof3021.controller.productController.CrudMvcMethod;
import com.nhom2.asmsof3021.model.Monitor;
import com.nhom2.asmsof3021.model.Product;
import com.nhom2.asmsof3021.model.ProductListImage;
import com.nhom2.asmsof3021.repository.productRepo.MonitorRepo;
import com.nhom2.asmsof3021.repository.productRepo.ProductRepo;
import com.nhom2.asmsof3021.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/product/management/Monitor")
public class MonitorController extends CrudMvcMethod<Monitor,Integer> {
    private final MonitorRepo repo;
    private final ProductService productService;
    @Override
    @PostMapping("/create")
    public String create(@ModelAttribute Monitor monitor, @RequestPart("images") MultipartFile[] images, @RequestPart("imagePhoto")MultipartFile photoImage, RedirectAttributes redirectAttributes){
        List<ProductListImage> getImages = productService.saveImage(monitor,images,photoImage);
        monitor.setProductListImages(getImages);
        repo.save(monitor);
        return "redirect:/admin/product/management";
    }

    @Override
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Monitor monitor=repo.findById(id).orElseThrow();
        if(monitor!=null){
            productService.deleteProduct(id);
        }
        return "redirect:/admin/product/management";
    }

    @Override
    @PostMapping("/update/{id}")

    public String update(Monitor monitor, @PathVariable Integer id,@RequestPart("images") MultipartFile[] images, @RequestPart("imagePhoto")MultipartFile photoImage, RedirectAttributes redirectAttributes) {
        if(repo.findById(id).isPresent()){
            List<ProductListImage> getImages = productService.saveImage(monitor,images,photoImage);
            monitor.setProductListImages(getImages);
            repo.save(monitor);
            redirectAttributes.addFlashAttribute("statusMessage","Success");
        }
        return "redirect:/admin/product/management";
    }



}
