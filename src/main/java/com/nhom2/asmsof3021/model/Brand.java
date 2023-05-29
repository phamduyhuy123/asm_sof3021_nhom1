package com.nhom2.asmsof3021.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@Builder
@Data
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int brandId;
    private String name;

    public Brand() {

    }
}
