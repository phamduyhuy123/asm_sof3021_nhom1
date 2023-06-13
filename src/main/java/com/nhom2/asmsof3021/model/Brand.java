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
    @OneToMany(mappedBy = "brand",fetch = FetchType.EAGER)
    private List<Product> products;
    @OneToMany(mappedBy = "brand", fetch = FetchType.EAGER)
    private List<ProductLine> productLines;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "brand_category",
            joinColumns = @JoinColumn(name = "brand_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;
}
