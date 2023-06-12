package com.nhom2.asmsof3021.controller.productController.admin;

import com.nhom2.asmsof3021.controller.productController.CrudMvcMethod;
import com.nhom2.asmsof3021.model.Monitor;
import com.nhom2.asmsof3021.model.Product;
import com.nhom2.asmsof3021.model.ProductListImage;
import com.nhom2.asmsof3021.repository.productRepo.MonitorRepo;
import com.nhom2.asmsof3021.repository.productRepo.ProductRepo;
import com.nhom2.asmsof3021.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/product/management/Monitor")
public class MonitorController extends CrudMvcMethod<Monitor,Integer> {
    private final MonitorRepo repo;
    private final ProductRepo productRepo;
    private final ProductService productService;
    @Override
    @PostMapping("/create")
    public String create(@ModelAttribute Monitor monitor, @RequestPart("images") MultipartFile[] images,@RequestPart("imagePhoto") MultipartFile photoImage, RedirectAttributes redirectAttributes) {
        // Tạo danh sách để lưu các đối tượng ProductListImage
        List<ProductListImage> productListImages = new ArrayList<>();

        // Iterate over the images and create ProductListImage objects
        for (MultipartFile image : images) {
            // Lưu tệp tin vào thư mục "/static/images/product"
            String fileName = image.getOriginalFilename();
            System.out.println(fileName);

            // Tạo đối tượng ProductListImage và gán tên tệp tin
            ProductListImage productListImage = new ProductListImage();
            productListImage.setFileName(fileName);
            productListImage.setProduct(monitor);
            // Thêm đối tượng vào danh sách
            productListImages.add(productListImage);
        }
        monitor.setImage(photoImage.getOriginalFilename());

        // Set the productListImages list to the monitor object
        monitor.setProductListImages(productListImages);


        productService.save(monitor);
        return "redirect:/admin/product/management";
    }

    @Override
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Monitor monitor=repo.findById(id).orElseThrow();
        if(monitor!=null){
            productService.delete(id);
        }
        return "redirect:/admin/product/management";
    }

    @Override
    @PostMapping("/update/{id}")

    public String update(Monitor monitor, @PathVariable Integer id, @RequestParam("images") MultipartFile[] images,@RequestPart("imagePhoto") MultipartFile photoImage, RedirectAttributes redirectAttributes) {
        if(repo.findById(id).isPresent()){
            // Tạo danh sách để lưu các đối tượng ProductListImage
            List<ProductListImage> productListImages = new ArrayList<>();

            // Iterate over the images and create ProductListImage objects
            for (MultipartFile image : images) {
                // Lưu tệp tin vào thư mục "/static/images/product"
                String fileName = image.getOriginalFilename();
                System.out.println(fileName);

                // Tạo đối tượng ProductListImage và gán tên tệp tin
                ProductListImage productListImage = new ProductListImage();
                productListImage.setFileName(fileName);
                productListImage.setProduct(monitor);
                // Thêm đối tượng vào danh sách
                productListImages.add(productListImage);
            }
            monitor.setImage(photoImage.getOriginalFilename());

            // Set the productListImages list to the monitor object
            monitor.setProductListImages(productListImages);
            repo.save(monitor);
            redirectAttributes.addFlashAttribute("statusMessage","Success");
        }
        return "redirect:/admin/product/management";
    }




}
