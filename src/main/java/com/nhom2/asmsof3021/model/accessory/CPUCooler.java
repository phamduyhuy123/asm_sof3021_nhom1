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
public class CPUCooler extends Product {
    private String productSize;
    private String heatsinkSize;
    private double weight;
    private String heatpipe;
    private String fanSize;
    private String fanSpeed;
    private double fanAirflow;
    private double fanAirPressure;
    private double fanNoiseLevel;
    private String fanConnector;
    private String bearingType;
    private String fanRatedVoltage;
    private double fanRatedCurrent;
    private double fanPowerConsumption;
    private String ledType;
}
