package com.nhom2.asmsof3021.service;

import com.nhom2.asmsof3021.model.Brand;
import com.nhom2.asmsof3021.model.Category;
import com.nhom2.asmsof3021.model.Product;
import com.nhom2.asmsof3021.model.ProductListImage;
import com.nhom2.asmsof3021.repository.BrandRepository;
import com.nhom2.asmsof3021.repository.CategoryRepo;
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
    public void createProduct(Product newProduct) {
        Category existingCategory = null;
        Optional<List<Category>> existingCategoryOptional= categoryRepo.findByName(newProduct.getCategory().getName());
        if (existingCategoryOptional.isPresent()){
            existingCategory = existingCategoryOptional.get().get(0);
        }
        Brand existingBrand = null;
        Optional<List<Brand>> exiOptionalBrand = brandRepository.findByName(newProduct.getBrand().getName());
        if (exiOptionalBrand.isPresent()){
            existingBrand = exiOptionalBrand.get().get(0);
        }

        if (existingCategory == null && existingBrand == null) {
            // Category và Brand đều không tồn tại, thêm mới vào cả ba bảng
            categoryRepo.save(newProduct.getCategory());
            brandRepository.save(newProduct.getBrand());
            productRepo.save(newProduct);
        } else if (existingCategory == null) {
            // Category không tồn tại, thêm mới Category và thêm vào bảng Product
            categoryRepo.save(newProduct.getCategory());
            productRepo.save(newProduct);
        } else if (existingBrand == null) {
            // Brand không tồn tại, thêm mới Brand và thêm vào bảng Product
            brandRepository.save(newProduct.getBrand());
            productRepo.save(newProduct);
        } else {
            // Cả Category và Brand đã tồn tại, chỉ thêm vào bảng Product
            productRepo.save(newProduct);
        }
    }
    public void save(Product product){
        productRepo.save(product);
    }
    @Transactional
    public void delete(Integer id){
        productRepo.deleteById(id);
    }
    public List<ProductListImage> saveFiles(MultipartFile[] images, MultipartFile photoImage, Product product){
        // Tạo danh sách để lưu các đối tượng ProductListImage
        List<ProductListImage> productListImages = new ArrayList<>();

        try{
            // Lưu tệp tin vào thư mục "/static/images/product"
            String fileName = photoImage.getOriginalFilename();
            Path destinationPath = Paths.get("src/main/resources/static/images/product/", fileName);
            Files.copy(photoImage.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

        }catch(Exception e){
        }
        //Xử lý nhiều file

        for (int i = 0; i < images.length; i++) {

            MultipartFile file = images[i];

            try{
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

            }catch(Exception e){
            }
        }
        product.setImage(photoImage.getOriginalFilename());
        return productListImages;
    }
}
