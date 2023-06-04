package com.nhom2.asmsof3021.service;


import com.nhom2.asmsof3021.model.Category;
import com.nhom2.asmsof3021.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryService {
    @Autowired
    private CategoryRepo repo;
    public List<Category> findAll(){
        return (List<Category>) repo.findAll();
    }

    public Category finById(int id){
        return repo.findById(id).stream().findFirst().get();
    }
}
