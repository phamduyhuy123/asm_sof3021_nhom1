package com.nhom2.asmsof3021.model;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Computer extends Product {
    private String cpu;
    private String mainboard;
    private String ram;
    private String vga;
    private String ssd;
    private String pcCase;
    private String powerSupply;
    private String hdd;
    private String cpuCooler;
    private String caseFan;

    // Constructors, getters, and setters
}
