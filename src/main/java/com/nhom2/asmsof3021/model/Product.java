package com.nhom2.asmsof3021.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "product")
@Inheritance(strategy = InheritanceType.JOINED)

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public abstract class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int id;
    private String name;
    private String image;
    private BigDecimal price;
    private int disCount;
    private String baohanh;
    private Integer stock;
    @Transient
    private Integer quantity;

    @OneToMany(mappedBy = "product",fetch = FetchType.EAGER)
    @JsonBackReference
    private List<ProductListImage> productListImages;
    @OneToOne
    @JsonBackReference
    private Brand brand;
    @OneToOne
    @JsonBackReference
    private Category category;
    @OneToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    private ProductLine productLine;

}
