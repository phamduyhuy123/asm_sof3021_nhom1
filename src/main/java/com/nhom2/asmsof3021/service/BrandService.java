package com.nhom2.asmsof3021.service;


import com.nhom2.asmsof3021.model.Brand;
import com.nhom2.asmsof3021.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {
   @Autowired
    private BrandRepository repo;
    public List<Brand> findAll(){
        return (List<Brand>) repo.findAll();
    }
    public Brand findById(int id){

        return repo.findById(id).stream().findFirst().get();
    }
}
