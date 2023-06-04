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
    private Double weight;
    private String heatpipe;
    private String fanSize;
    private String fanSpeed;
    private Double fanAirflow;
    private Double fanAirPressure;
    private Double fanNoiseLevel;
    private String fanConnector;
    private String bearingType;
    private String fanRatedVoltage;
    private Double fanRatedCurrent;
    private Double fanPowerConsumption;
    private String ledType;
}
