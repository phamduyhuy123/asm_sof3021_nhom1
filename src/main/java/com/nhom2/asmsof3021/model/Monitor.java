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
public class Monitor extends Product {
    private String screenSize;
    private String aspectRatio;
    private String panelType;
    private String displayColor;
    private String responseTime;
    private Integer refreshRate;
    private Boolean flickerFree;
    private Integer staticContrastRatio;
    private String inputConnectors;
    private String powerConsumption;
    private String wallMountPattern;
    private Boolean hasSpeakers;


    // Constructors, getters, and setters
}

