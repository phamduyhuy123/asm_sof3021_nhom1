package com.nhom2.asmsof3021.repository;

import com.nhom2.asmsof3021.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrdersRepository extends JpaRepository<Orders, UUID> {

}
