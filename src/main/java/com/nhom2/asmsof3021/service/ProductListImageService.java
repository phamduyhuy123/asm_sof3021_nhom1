package com.nhom2.asmsof3021.service;


import com.nhom2.asmsof3021.model.ProductListImage;
import com.nhom2.asmsof3021.repository.ProductListImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductListImageService {
    @Autowired
    private ProductListImageRepository repo;
    public List<ProductListImage> getListImageByProductId(int id){
        return repo.findByProduct_Id(id);
    }
}
