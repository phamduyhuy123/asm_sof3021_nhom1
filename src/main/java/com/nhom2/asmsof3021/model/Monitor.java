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
    private int refreshRate;
    private boolean flickerFree;
    private int staticContrastRatio;
    private String inputConnectors;
    private String powerConsumption;
    private String wallMountPattern;
    private boolean hasSpeakers;


    // Constructors, getters, and setters
}

