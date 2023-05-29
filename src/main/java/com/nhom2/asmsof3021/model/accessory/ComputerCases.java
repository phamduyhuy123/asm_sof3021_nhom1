package com.nhom2.asmsof3021.model.accessory;

import com.nhom2.asmsof3021.model.Product;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ComputerCases extends Product {
    private String material;
    private String formFactor;
    private String storageSupport;
    private String maxCPUCoolerHeight;
    private String maxVGALength;
    private String ioPorts;
    private int pciSlots;
    private String fanSupport;
    private String liquidCoolingSupport;
    private String powerSupplyType;
    private double weight;
    private double totalWeight;
}
