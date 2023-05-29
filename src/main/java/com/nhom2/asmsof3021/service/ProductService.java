package com.nhom2.asmsof3021.service;

import com.nhom2.asmsof3021.model.*;
import com.nhom2.asmsof3021.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepo repo;
    public List<Product> findAll(){
        return (List<Product>) repo.findAll();
    }

    public Brand findProducBrandtById(int id){
        Product product=repo.findById(id).stream().findFirst().get();

        return product.getBrand();
    }

    public List<Product> findProductCategoryById(int id){

        return repo.findByCategory_CatalogId(id);
    }
    public ProductLine findProductLineById(int id){
        Product product=repo.findById(id).stream().findFirst().get();

        return product.getProductLine();
    }
    public Product findProductById(Integer id){
        Optional<Product> product=repo.findById(id);
        if(product.isPresent()){
            return product.get();
        }
        return null;
    }
}
