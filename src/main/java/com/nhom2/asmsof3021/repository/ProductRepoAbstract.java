package com.nhom2.asmsof3021.repository;


import com.nhom2.asmsof3021.model.Computer;
import com.nhom2.asmsof3021.model.Laptop;
import com.nhom2.asmsof3021.model.Monitor;
import com.nhom2.asmsof3021.model.Product;
import com.nhom2.asmsof3021.model.accessory.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@NoRepositoryBean
public interface ProductRepoAbstract<T extends Product> extends JpaRepository<T,Integer> {
    Iterable<T> findAllByProductLine_ProductLineId(int productLineId);
    List<T> findByCategory_CatalogId(int id);


}
