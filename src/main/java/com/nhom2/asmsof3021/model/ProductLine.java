package com.nhom2.asmsof3021.model;

import jakarta.persistence.*;
import lombok.*;

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
    @Column(unique = true)
    private String name;
    @ManyToOne(cascade = {CascadeType.MERGE})
    private Brand brand;
}
