package com.nhom2.asmsof3021.controller.productController;

import com.nhom2.asmsof3021.factory.ProductFactory;
import com.nhom2.asmsof3021.model.Category;
import com.nhom2.asmsof3021.model.Product;
import com.nhom2.asmsof3021.repository.CategoryRepo;
import com.nhom2.asmsof3021.repository.productRepo.ProductRepo;
import com.nhom2.asmsof3021.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.metamodel.EntityType;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.*;

import static com.nhom2.asmsof3021.controller.AdminPageController.productManagementDefault;
import static com.nhom2.asmsof3021.utils.AuthenticateUtil.checkIsAuthenticated;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final CategoryRepo categoryRepository;
    private final HttpSession session;
    private final ProductRepo productRepo;
    private final UserRepository userRepository;
    private final Map<Integer, ProductFactory> factoryMap;


    @GetMapping("/admin/product/category/{id}")
    public String getCategory(@PathVariable Integer id, Model model, @RequestParam(defaultValue = "1") int page) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + id));
        model.addAttribute("categoryViewName", "admin/product/" + category.getEntityClassName());


        return category.getEntityClassName();
    }


    @GetMapping("/admin/product/EDIT/{id}")
    public String edit(@PathVariable Integer id, Model model, Principal principal) {
        checkIsAuthenticated(principal, session, userRepository);

        Product product = productRepo.findById(id).orElseThrow();
        model.addAttribute("product", product);

        if (product.getCategory()==null){
            System.out.println("hello ");
            model.addAttribute("categoryViewName", "khongtimthay" );
            model.addAttribute("categoryName", "product");
        }else{
            model.addAttribute("categoryViewName", "admin/product/" + product.getCategory().getEntityClassName());
            model.addAttribute("categoryName", product.getCategory().getEntityClassName());
        }

        return "forward:/admin/product/management";
    }



    @GetMapping("/admin/product/category/new")
    public String newForm(@RequestParam("categoryId") Integer id, Model model) {


        Category category = categoryRepository.findById(id).orElseThrow();
        String categoryEntityClassName = category.getEntityClassName();
        String categoryName = category.getName();
        ProductFactory productFactory = factoryMap.get(id);
        if (productFactory == null) {
            throw new IllegalArgumentException("Invalid categoryId");
        }
        Product product = productFactory.createProduct();
        product.setCategory(category);
        System.out.println("productListImage: "+product.getProductListImages());
        product.setProductListImages(new ArrayList<>());
        model.addAttribute("categoryViewName", "admin/product/" + categoryEntityClassName);
        model.addAttribute("categoryName", categoryEntityClassName);
        model.addAttribute("product", product);
        return "forward:/admin/product/management";
    }

    @GetMapping("/product/{id}")
    public String productDetail(Model model, @PathVariable Integer id) {
        Product product = productRepo.findById(id).orElseThrow();
        model.addAttribute("productDetail", product);
        model.addAttribute("productsSimilar", getProductsSimilar(product));
        return "ProductDetail";
    }

//    @GetMapping("/product/api/count/similar/{id}")
//    public ResponseEntity<Integer> productsSimilar(@PathVariable Integer id) {
//        Product product = productRepo.findById(id).orElseThrow();
//        Set<Product> productsSimilar = getProductsSimilar(product);
//        if (productsSimilar.isEmpty()) {
//            throw new NoSuchElementException("No value present");
//        }
//        return ResponseEntity.ok(productRepo.findAll().size());
//    }

    private Set<Product> getProductsSimilar(Product product) {
        Integer id = product.getId();
        Integer brandId = product.getBrand() != null ? product.getBrand().getBrandId() : null;
        Integer catalogId = product.getCategory() != null ? product.getCategory().getCatalogId() : null;
        Integer productLineId = product.getProductLine() != null ? product.getProductLine().getProductLineId() : null;
        Set<Product> productsSimilar = new HashSet<>();
        if (brandId != null && catalogId != null && productLineId != null) {
            productsSimilar = productRepo.findAllByBrand_BrandIdAndCategory_CatalogIdAndProductLine_ProductLineId(brandId, catalogId, productLineId);
        } else if (brandId != null && catalogId != null) {
            productsSimilar = productRepo.findAllByBrand_BrandIdAndCategory_CatalogId(brandId, catalogId);
        } else if (catalogId != null && productLineId != null) {
            productsSimilar = productRepo.findAllByCategory_CatalogIdAndProductLine_ProductLineId(catalogId, productLineId);
        } else if (catalogId != null) {
            productsSimilar = productRepo.findAllByCategory_CatalogId(catalogId);
        } else if (brandId != null) {
            productsSimilar = productRepo.findAllByBrand_BrandId(brandId);
        } else if (productLineId != null) {
            productsSimilar = productRepo.findAllByProductLine_ProductLineId(productLineId);
        } else {
            productsSimilar = new HashSet<>();
        }
        for (Product p : productsSimilar
        ) {
            if (p.getId() == id) {
                productsSimilar.remove(p);
                break;
            }
        }

        return productsSimilar;
    }

}
