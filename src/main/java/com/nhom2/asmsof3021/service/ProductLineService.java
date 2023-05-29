package com.nhom2.asmsof3021.service;


import com.nhom2.asmsof3021.model.ProductLine;
import com.nhom2.asmsof3021.repository.ProductLineRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductLineService {
    @Autowired
    private ProductLineRepo repo;
    public List<ProductLine> findAll(){
        return (List<ProductLine>) repo.findAll();
    }
    public ProductLine finProductLineById(int id){
        return repo.findById(id).stream().findFirst().get();
    }
}
