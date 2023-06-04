package com.nhom2.asmsof3021.model.gear;

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
public class OpticalMouse extends Product {
    private String sensorType;
    private Integer dpi;
    private Integer maxAcceleration;
    private Integer maxSpeed;
    private Integer weight;
    private Double height;
    private Double width;
    private Double depth;
    private Integer buttonCount;
    private String connectionType;
    private Boolean hasLED;
    private Integer hardwareWarranty;
}
