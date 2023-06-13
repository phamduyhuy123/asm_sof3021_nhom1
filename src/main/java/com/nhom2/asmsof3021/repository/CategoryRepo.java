package com.nhom2.asmsof3021.repository;


import com.nhom2.asmsof3021.model.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepo extends CrudRepository<Category,Integer> {
    @Query("SELECT c from Category  c where c.entityClassName is not null  ")
    List<Category> findAllByEntityClassNameExists();

}
