package com.nhom2.asmsof3021.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer catalogId;
    private String name;
    private String entityClassName;
    @OneToMany(mappedBy = "category")
    private List<Product> products;
    @OneToMany(mappedBy = "category",fetch = FetchType.EAGER)
    private List<ProductLine> productLines;
    @ManyToMany(mappedBy = "categories",fetch = FetchType.EAGER)
    private List<Brand> brands;
}

