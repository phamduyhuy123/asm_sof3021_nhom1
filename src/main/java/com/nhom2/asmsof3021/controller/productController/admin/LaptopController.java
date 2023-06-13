package com.nhom2.asmsof3021.controller.productController.admin;

import com.nhom2.asmsof3021.controller.productController.CrudMvcMethod;
import com.nhom2.asmsof3021.model.Laptop;
import com.nhom2.asmsof3021.model.Monitor;
import com.nhom2.asmsof3021.model.ProductListImage;
import com.nhom2.asmsof3021.repository.productRepo.LaptopRepo;
import com.nhom2.asmsof3021.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/product/management/Laptop")
public class LaptopController extends CrudMvcMethod<Laptop, Integer> {
    private final LaptopRepo repo;
    private final ProductService productService;

    @Override
    @PostMapping("/create")
    public String create(@ModelAttribute Laptop laptop, @RequestPart("images") MultipartFile[] images, @RequestPart("imagePhoto")MultipartFile photoImage, RedirectAttributes redirectAttributes){
        List<ProductListImage> getImages = productService.saveImage(laptop,images,photoImage);
//        laptop.setImage(photoImage.getOriginalFilename());
        laptop.setProductListImages(getImages);

        repo.save(laptop);
        return "redirect:/admin/product/management";
    }

    @Override
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Laptop laptop=repo.findById(id).orElseThrow();
        if(laptop!=null){
            productService.deleteProduct(id);
        }
        return "redirect:/admin/product/management";
    }

    @Override
    @PostMapping("/update/{id}")
    public String update(Laptop laptop, @PathVariable Integer id,@RequestPart("images") MultipartFile[] images, @RequestPart("imagePhoto")MultipartFile photoImage, RedirectAttributes redirectAttributes) {
        if(repo.findById(id).isPresent()){
            List<ProductListImage> getImages = productService.saveImage(laptop,images,photoImage);
            laptop.setProductListImages(getImages);
            repo.save(laptop);
            redirectAttributes.addFlashAttribute("statusMessage","Success");
        }
        return "redirect:/admin/product/management";
    }
}
