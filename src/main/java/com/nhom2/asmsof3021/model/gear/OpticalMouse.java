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
    private int dpi;
    private int maxAcceleration;
    private int maxSpeed;
    private int weight;
    private double height;
    private double width;
    private double depth;
    private int buttonCount;
    private String connectionType;
    private boolean hasLED;
    private int hardwareWarranty;
}
