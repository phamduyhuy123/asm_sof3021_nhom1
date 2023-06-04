package com.nhom2.asmsof3021.repository.productRepo;

import com.nhom2.asmsof3021.model.Computer;
import com.nhom2.asmsof3021.repository.ProductRepoAbstract;
import org.springframework.transaction.annotation.Transactional;

@Transactional

public interface ComputerRepo extends ProductRepoAbstract<Computer> {
    // Add any additional methods specific to the computer repository
}