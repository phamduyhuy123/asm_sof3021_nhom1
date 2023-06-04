package com.nhom2.asmsof3021.repository.productRepo;

import com.nhom2.asmsof3021.model.Product;
import com.nhom2.asmsof3021.repository.ProductRepoAbstract;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProductRepo extends ProductRepoAbstract<Product> {

}