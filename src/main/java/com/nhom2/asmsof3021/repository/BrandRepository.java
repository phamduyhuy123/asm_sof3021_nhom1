package com.nhom2.asmsof3021.repository;


import com.nhom2.asmsof3021.model.Brand;
import com.nhom2.asmsof3021.model.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends CrudRepository<Brand,Integer> {
    @Query("SELECT c from Brand  c where c.name is not null  ")
    List<Brand> findAllByNameExists();
}
