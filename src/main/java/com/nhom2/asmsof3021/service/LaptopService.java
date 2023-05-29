package com.nhom2.asmsof3021.service;


import com.nhom2.asmsof3021.model.Laptop;
import com.nhom2.asmsof3021.repository.LaptopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LaptopService{
    @Autowired
    private LaptopRepository repo;


    public List<Laptop> findAll() {
        return (List<Laptop>) repo.findAll();
    }
    public List<Laptop> findlaptopByBrand_Category_ProductLine_Id(int catalogId,int brandId, int productLineId ){
        return repo.findLaptopByCategory_CatalogIdAndBrand_BrandIdAndProductLine_ProductLineId(catalogId,brandId,productLineId);
    }
    public List<Laptop> findLaptopByCategory_Id(int catalogId){
        return repo.findLaptopByCategory_CatalogId(catalogId);
    }
    public List<Laptop> findLaptopByCategory_Brand_Id(int catalogId,int brandId){
        return repo.findLaptopByCategory_CatalogIdAndBrand_BrandId(catalogId,brandId);
    }

}
