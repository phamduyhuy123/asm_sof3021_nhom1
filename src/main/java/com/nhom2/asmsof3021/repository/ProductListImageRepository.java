package com.nhom2.asmsof3021.repository;


import com.nhom2.asmsof3021.model.ProductListImage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductListImageRepository  extends CrudRepository<ProductListImage,Long> {
    List<ProductListImage> findByProduct_Id(int id);

}
