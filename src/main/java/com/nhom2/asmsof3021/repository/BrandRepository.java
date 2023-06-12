package com.nhom2.asmsof3021.repository;


import com.nhom2.asmsof3021.model.Brand;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends CrudRepository<Brand,Integer> {
    Optional<List<Brand>> findByName(String name);
}
