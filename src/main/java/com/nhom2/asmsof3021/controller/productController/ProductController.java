package com.nhom2.asmsof3021.controller.productController;

import com.nhom2.asmsof3021.controller.AdminPageController;
import com.nhom2.asmsof3021.model.Category;
import com.nhom2.asmsof3021.model.Laptop;
import com.nhom2.asmsof3021.model.Product;
import com.nhom2.asmsof3021.repository.CategoryRepo;
import com.nhom2.asmsof3021.repository.productRepo.ProductRepo;
import com.nhom2.asmsof3021.security.UserRepository;
import com.nhom2.asmsof3021.service.CategoryService;
import com.nhom2.asmsof3021.service.ProductService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.metamodel.EntityType;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static com.nhom2.asmsof3021.utils.AuthenticateUtil.checkIsAuthenticated;

@Controller
@RequiredArgsConstructor
public class ProductController  {
    private final CategoryRepo categoryRepository;
    private final HttpSession session;
    @PersistenceContext
    private final EntityManager entityManager;
    private final ProductRepo productRepo;
    private final UserRepository userRepository;
    @GetMapping("/admin/product/generate-input-fields/{categoryId}")
    @ResponseBody
    public String generateInputFieldProductDependOnCategory(@PathVariable Integer categoryId){
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + categoryId));

        return generateInputFieldsHtml(category);
    }
    private  String generateInputFieldsHtml(Category category) {
        String entityClassName = category.getEntityClassName();

        Class<?> entityClass;
        try {
            entityClass = Class.forName("com.nhom2.asmsof3021.model."+entityClassName);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Invalid entity class name: " + entityClassName, e);
        }


        EntityType<?> entityType = entityManager.getMetamodel().entity(entityClass);

        StringBuilder inputFieldsHtml = new StringBuilder();
        Class<?> entityProduct;
        try {
            entityProduct = Class.forName("com.nhom2.asmsof3021.model.Product");
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Invalid entity class name: Product" , e);
        }
        EntityType<?> entityTypeProduct = entityManager.getMetamodel().entity(entityProduct);


        entityType.getAttributes().forEach(attribute -> {
            String attributeName = attribute.getName();
            Class<?> attributeType = attribute.getJavaType();
            String inputType = "text"; // Default input type
            if (attributeType == Boolean.class) {
                inputType = "checkbox";
            } else if (attributeType == Integer.class ||
                    attributeType == Double.class ||
                    attributeType == BigDecimal.class) {
                inputType = "number";
            }
            if(!entityTypeProduct.getAttributes().contains(attribute)) {
                if(inputType.equals("checkbox")){
                    inputFieldsHtml
                            .append("<div class=\" form-check\">")
                            .append("<input  class=\"form-check-input\"").append("type ='").append(inputType+"' ").append(" name='")
                            .append(attributeName+"'")
                            .append(" id='").append(attributeName+"Id' >")
                            .append("<label class=\"form-check-label\" ")
                            .append(" for='").append(attributeName+"Id").append("' >")
                            .append(attributeName)
                            .append("</label>")
                            .append("</div>");
                }else {
                    inputFieldsHtml
                            .append("<div class=\"mb-3\">")
                            .append("<label class=\"form-label\"> ")
                            .append(attributeName)
                            .append("</label>")
                            .append("<input type=\"").append(inputType).append("\" class=\"form-control\" name='")
                            .append(attributeName).append("'>")
                            .append("</div>");
                }

            }

        });

        return inputFieldsHtml.toString();
    }
    @GetMapping("/admin/product/category/{id}")
    public String getCategory(@PathVariable Integer id,Model model){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + id));
        model.addAttribute("categoryViewName","admin/product/"+category.getEntityClassName());
        return category.getEntityClassName();
    }

    @GetMapping("/admin/product/EDIT/{id}")
    public String edit(@PathVariable Integer id, Model model, Principal principal){
        checkIsAuthenticated(principal,session,userRepository);
        List<Category> categoryList=categoryRepository.findAllByEntityClassNameExists();
        model.addAttribute("categories" ,categoryList);
        List<AdminPageController.BreadcrumbLink> breadcrumbLinkList=new ArrayList<>();
        breadcrumbLinkList.add(new AdminPageController.BreadcrumbLink("Product","/admin/product"));
        breadcrumbLinkList.add(new AdminPageController.BreadcrumbLink("Product Management","/admin/product/management"));
        session.setAttribute("breadcrumbs", breadcrumbLinkList);
        Product product = productRepo.findById(id).orElseThrow();
        model.addAttribute("product",product);
        model.addAttribute("categoryViewName","admin/product/"+product.getCategory().getEntityClassName());
        List<Product> list = productRepo.findAll();
        model.addAttribute("products",list);
        System.out.println();
        return "admin/productManagement";
    }
    @GetMapping("/admin/product/category/new")
    public String newForm(@RequestParam("categoryId") Integer id, Model model){
        Category category = categoryRepository.findById(id).orElseThrow();
        model.addAttribute("categoryViewName","admin/product/"+category.getEntityClassName());

        return "admin/productManagement";
    }
}
