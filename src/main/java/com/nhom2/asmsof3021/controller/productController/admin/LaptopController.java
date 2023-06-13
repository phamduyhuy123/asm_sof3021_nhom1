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

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/product/management/Laptop")
public class LaptopController extends CrudMvcMethod<Laptop, Integer> {
    private final LaptopRepo repo;
    private final ProductService productService;

    @Override
    @PostMapping("/create")
    public String create(@ModelAttribute Laptop laptop, @RequestParam("images") MultipartFile[] images, @RequestPart("imagePhoto") MultipartFile photoImage, RedirectAttributes redirectAttributes) {
        System.out.println("laptop brand: "+laptop.getBrand());
        System.out.println("laptop productline: "+laptop.getProductLine());
        List<ProductListImage> productListImages = productService.saveFiles(images,photoImage, laptop);
        // Set the productListImages list to the monitor object
        System.out.println("ListimageLaptop: "+productListImages);
        laptop.setProductListImages(productListImages);

        productService.save(laptop);
        return "redirect:/admin/product/management";
    }

    @Override
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Laptop laptop = repo.findById(id).orElseThrow();
        System.out.println(laptop);
        if (laptop != null) {
            productService.delete(id);
        }
        return "redirect:/admin/product/management";
    }

    @Override
    @PostMapping("/update/{id}")
    public String update(Laptop laptop, @PathVariable Integer id, @RequestParam("images") MultipartFile[] images, @RequestPart("imagePhoto") MultipartFile photoImage, RedirectAttributes redirectAttributes) {
        if (repo.findById(id).isPresent()) {
            List<ProductListImage> productListImages = new ArrayList<>();

            // Iterate over the images and create ProductListImage objects
            for (MultipartFile image : images) {
                // Lưu tệp tin vào thư mục "/static/images/product"
                String fileName = image.getOriginalFilename();
                System.out.println(fileName);

                // Tạo đối tượng ProductListImage và gán tên tệp tin
                ProductListImage productListImage = new ProductListImage();
                productListImage.setFileName(fileName);
                productListImage.setProduct(laptop);
                // Thêm đối tượng vào danh sách
                productListImages.add(productListImage);
            }
            laptop.setImage(photoImage.getOriginalFilename());

            // Set the productListImages list to the monitor object
            laptop.setProductListImages(productListImages);
            productService.save(laptop);
            redirectAttributes.addFlashAttribute("statusMessage", "Success");
        }
        return "redirect:/admin/product/management";
    }
}
