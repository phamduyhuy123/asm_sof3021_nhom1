package com.nhom2.asmsof3021.model;

import jakarta.persistence.*;
import lombok.*;

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
}

