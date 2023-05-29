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
public class Mainboard extends Product {
    private String chipset;
    private String socket;
    private int ramSlots;
    private String formFactor;
    private String storageSupport;
}
