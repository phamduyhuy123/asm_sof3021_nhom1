package com.nhom2.asmsof3021.repository;


import com.nhom2.asmsof3021.model.Laptop;
import com.nhom2.asmsof3021.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer> {
    Iterable<Product> findAllByProductLine_ProductLineId(int productLineId);
    List<Product> findByCategory_CatalogId(int id);
}
