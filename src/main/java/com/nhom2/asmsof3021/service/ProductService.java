package com.nhom2.asmsof3021.service;

import com.nhom2.asmsof3021.model.*;
import com.nhom2.asmsof3021.repository.BrandRepository;
import com.nhom2.asmsof3021.repository.CategoryRepo;
import com.nhom2.asmsof3021.repository.ProductLineRepo;
import com.nhom2.asmsof3021.repository.productRepo.ProductRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    @PersistenceContext
    private EntityManager entityManager;

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final BrandRepository brandRepository;
    private final ProductLineRepo productLineRepo;
    private final HttpSession session;

    public List<Product> findProductsByFilters(List<Integer> categoryIds, List<Integer> brandIds, List<Integer> productLineIds) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);
        List<Predicate> predicates = new ArrayList<>();
        if (categoryIds != null && !categoryIds.isEmpty()) {
            predicates.add(root.get("category").get("catalogId").in(categoryIds));
        }
        if (brandIds != null && !brandIds.isEmpty()) {
            predicates.add(root.get("brand").get("brandId").in(brandIds));
        }
        if (productLineIds != null && !productLineIds.isEmpty()) {
            predicates.add(root.get("productLine").get("productLineId").in(productLineIds));
        }
        query.select(root).where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(query).getResultList();
    }

    public Page<Product> getAllProducts(int page, int size, String sortField, Sort.Direction sortDirection) {
        Sort sort = Sort.by(sortDirection, sortField);
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepo.findAll(pageable);
    }

    @Transactional
    public void save(Product product) {
        List<Brand> existingBrand = brandRepository.findByName(product.getName());
        List<ProductLine> existingProductLine = productLineRepo.findByName(product.getName());
        System.out.println("teeeeeeeeeeeeeee: "+existingBrand);
        System.out.println("teeeeeeeeeeeeeee: "+existingProductLine);

        productRepo.save(product);
    }


    @Transactional
    public void delete(Integer id) {
        productRepo.deleteById(id);
    }

    public List<ProductListImage> saveFiles(MultipartFile[] images, MultipartFile photoImage, Product product) {
        // Tạo danh sách để lưu các đối tượng ProductListImage
        List<ProductListImage> productListImages = new ArrayList<>();

        try {
            // Lưu tệp tin vào thư mục "/static/images/product"
            String fileName = photoImage.getOriginalFilename();
            Path destinationPath = Paths.get("src/main/resources/static/images/product/", fileName);
            Files.copy(photoImage.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

        } catch (Exception e) {
        }
        //Xử lý nhiều file

        for (int i = 0; i < images.length; i++) {

            MultipartFile file = images[i];

            try {
                // Lưu tệp tin vào thư mục "/static/images/product"
                String fileName = file.getOriginalFilename();
                Path destinationPath = Paths.get("src/main/resources/static/images/product/", fileName);
                Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

                // Tạo đối tượng ProductListImage và gán tên tệp tin
                ProductListImage productListImage = new ProductListImage();
                productListImage.setFileName(fileName);
                productListImage.setProduct(product);
                // Thêm đối tượng vào danh sách
                productListImages.add(productListImage);

            } catch (Exception e) {
            }
        }
        product.setImage(photoImage.getOriginalFilename());
        return productListImages;
    }
}
