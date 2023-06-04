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
    private Integer numberOfCores;
    private Integer numberOfPcores;
    private Integer numberOfEcores;
    private Integer numberOfThreads;
    private Double maxTurboFrequency;
    private Double maxTurboFrequencyPcore;
    private Double baseFrequencyPcore;
    private String cacheMemory;
    private String totalL2CacheMemory;
    private Integer basePower;
    private Integer maxTurboPower;
}
