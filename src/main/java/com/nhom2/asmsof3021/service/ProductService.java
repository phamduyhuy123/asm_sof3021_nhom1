package com.nhom2.asmsof3021.service;

import com.nhom2.asmsof3021.model.Product;
import com.nhom2.asmsof3021.repository.productRepo.ProductRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    @PersistenceContext
    private EntityManager entityManager;

    private final ProductRepo productRepo;
    public Page<Product> findProductsByFilters(List<Integer> categoryIds, List<Integer> brandIds, List<Integer> productLineIds, Pageable pageable) {
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
        query.distinct(true); // Add this line to eliminate duplicate results

        TypedQuery<Product> typedQuery = entityManager.createQuery(query);

        // Apply pagination
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());

        // Create a Pageable object with total results and apply pagination
        List<Product> products = typedQuery.getResultList();
        Long totalResults = getTotalResults(categoryIds, brandIds, productLineIds);
        Page<Product> page = new PageImpl<>(products, pageable, totalResults);

        return page;
    }
    public List<Product> searchProductResult(String searchTerm) {
        List<Product> productsSearch;
        if(searchTerm!=null || searchTerm.isEmpty()){
           productsSearch= productRepo.searchProduct(searchTerm);
            return productsSearch;
        }else {
            return null;
        }
    }

    private Long getTotalResults(List<Integer> categoryIds, List<Integer> brandIds, List<Integer> productLineIds) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Product> root = countQuery.from(Product.class);
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

        countQuery.select(cb.count(root)).where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(countQuery).getSingleResult();
    }

    public Page<Product> getAllProducts(int page, int size, String sortField, Sort.Direction sortDirection) {
        Sort sort = Sort.by(sortDirection, sortField);
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepo.findAll(pageable);
    }
    public void saveProduct(Product product){
        productRepo.save(product);
    }
}
