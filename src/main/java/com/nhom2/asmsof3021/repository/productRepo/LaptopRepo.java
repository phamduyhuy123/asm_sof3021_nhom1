package com.nhom2.asmsof3021.repository.productRepo;

import com.nhom2.asmsof3021.model.Laptop;
import com.nhom2.asmsof3021.repository.ProductRepoAbstract;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface LaptopRepo extends ProductRepoAbstract<Laptop> {
    // Add any additional methods specific to the laptop repository
}
