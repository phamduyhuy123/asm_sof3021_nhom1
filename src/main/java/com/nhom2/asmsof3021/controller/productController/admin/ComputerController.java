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

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/product/management/Computer")
public class ComputerController extends CrudMvcMethod<Computer, Integer> {
    private final ComputerRepo repo;
    private final ProductService productService;

    @Override
    @PostMapping("/create")
    public String create(@ModelAttribute Computer computer, @RequestParam("images") MultipartFile[] images,@RequestPart("imagePhoto") MultipartFile photoImage, RedirectAttributes redirectAttributes){
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
            productListImage.setProduct(computer);
            // Thêm đối tượng vào danh sách
            productListImages.add(productListImage);
        }
        computer.setImage(photoImage.getOriginalFilename());

        // Set the productListImages list to the monitor object
        computer.setProductListImages(productListImages);

        productService.save(computer);
        return "redirect:/admin/product/management";
    }

    @Override
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Computer computer=repo.findById(id).orElseThrow();
        if(computer!=null){
            productService.delete(id);
        }
        return "redirect:/admin/product/management";
    }

    @Override
    @PostMapping("/update/{id}")
    public String update(Computer computer, @PathVariable Integer id, @RequestParam("images") MultipartFile[] images,@RequestPart("imagePhoto") MultipartFile photoImage, RedirectAttributes redirectAttributes) {
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
                productListImage.setProduct(computer);
                // Thêm đối tượng vào danh sách
                productListImages.add(productListImage);
            }
            computer.setImage(photoImage.getOriginalFilename());

            // Set the productListImages list to the monitor object
            computer.setProductListImages(productListImages);
            repo.save(computer);
            redirectAttributes.addFlashAttribute("statusMessage","Success");
        }
        return "redirect:/admin/product/management";
    }
}
