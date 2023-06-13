package com.nhom2.asmsof3021.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDetail {
    @Id
    @GeneratedValue(strategy =GenerationType.UUID)
    private UUID id;
    private Integer productId;
    private Integer quantity;
    private String productName;
    private Integer disCount;
    private BigDecimal price;
    private BigDecimal totalPrice;
    private String image;
    @ManyToOne()
    private Orders orders;


}
