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
public class CPU extends Product {
    private int numberOfCores;
    private int numberOfPcores;
    private int numberOfEcores;
    private int numberOfThreads;
    private double maxTurboFrequency;
    private double maxTurboFrequencyPcore;
    private double baseFrequencyPcore;
    private String cacheMemory;
    private String totalL2CacheMemory;
    private int basePower;
    private int maxTurboPower;
}
