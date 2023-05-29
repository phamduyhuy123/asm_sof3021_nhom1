package com.nhom2.asmsof3021.repository;

import com.nhom2.asmsof3021.model.Laptop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaptopRepository extends JpaRepository<Laptop,Integer> {
    List<Laptop> findLaptopByCategory_CatalogIdAndBrand_BrandIdAndProductLine_ProductLineId(int catalogId, int brandId, int productLineId);
    List<Laptop> findLaptopByCategory_CatalogId(int catalogId);
    List<Laptop> findLaptopByCategory_CatalogIdAndBrand_BrandId(int catalogId,int brandId);
}
