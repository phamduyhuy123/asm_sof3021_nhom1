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
public class Headphone extends Product{
    private String type;
    private String driverType;
    private String connectivity;
    private String frequencyResponse;
    private String impedance;
    private double weight;
    private boolean hasMicrophone;
    private int hardwareWarranty;
}
