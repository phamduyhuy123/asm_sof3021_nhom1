package com.nhom2.asmsof3021.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer brandId;
    private String name;
    @OneToMany(mappedBy = "brand")
    private List<Product> products;
    @OneToMany(mappedBy = "brand")
    private List<ProductLine> productLines;
}
