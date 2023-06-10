package com.nhom2.asmsof3021.service;

import com.nhom2.asmsof3021.model.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @PersistenceContext
    private EntityManager entityManager;
    public List<Product> findProductsByFilters(List<Integer> categoryIds, List<Integer> brandIds, List<Integer> productLineIds) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);
        List<Predicate> predicates = new ArrayList<>();
        if (!categoryIds.isEmpty()) {
            predicates.add(root.get("category").get("catalogId").in(categoryIds));
        }
        if (!brandIds.isEmpty()) {
            predicates.add(root.get("brand").get("brandId").in(brandIds));
        }
        if (!productLineIds.isEmpty()) {
            predicates.add(root.get("productLine").get("productLineId").in(productLineIds));
        }
        query.select(root).where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(query).getResultList();
    }
}
