package com.nhom2.asmsof3021.repository;


import com.nhom2.asmsof3021.model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends CrudRepository<Category,Integer> {

}
