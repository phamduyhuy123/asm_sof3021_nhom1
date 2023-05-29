package com.nhom2.asmsof3021.repository;


import com.nhom2.asmsof3021.model.Brand;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends CrudRepository<Brand,Integer> {

}
