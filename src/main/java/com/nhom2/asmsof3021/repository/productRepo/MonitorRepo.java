package com.nhom2.asmsof3021.repository.productRepo;

import com.nhom2.asmsof3021.model.Monitor;
import com.nhom2.asmsof3021.repository.ProductRepoAbstract;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MonitorRepo extends ProductRepoAbstract<Monitor> {
    // Add any additional methods specific to the monitor repository
}