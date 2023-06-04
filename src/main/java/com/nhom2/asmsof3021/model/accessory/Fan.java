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
public class Fan extends Product {
    private Integer warranty;
    private String size;
    private String bearingType;
    private String ledType;
    private String ledDisplayRing;
    private String fanSpeed;
    private String operatingVoltage;
    private String powerDraw;
    private String noiseLevel;
    private String airflow;
    private String airPressure;
    private String lifespan;
    private Integer includedFans;
    private String accessories;
}
