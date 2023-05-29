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
public class Ram extends Product {
    private int capacity;
    private String type;
    private int speed;
    private String memoryBus;
    private String casLatency;
}
