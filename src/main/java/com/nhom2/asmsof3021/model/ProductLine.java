package com.nhom2.asmsof3021.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productLineId;
    private String name;
    @ManyToOne
    private Brand brand;
    @ManyToOne
    private Category category;
    @OneToMany(mappedBy = "productLine")
    private List<Product> products;


}
