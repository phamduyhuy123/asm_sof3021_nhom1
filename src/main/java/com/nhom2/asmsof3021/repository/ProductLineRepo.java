package com.nhom2.asmsof3021.repository;


import com.nhom2.asmsof3021.model.Category;
import com.nhom2.asmsof3021.model.Product;
import com.nhom2.asmsof3021.model.ProductLine;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductLineRepo extends CrudRepository<ProductLine,Integer> {
    List<ProductLine> findByName(String name);
}
