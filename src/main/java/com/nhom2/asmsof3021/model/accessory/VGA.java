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
public class VGA extends Product {
    private String gpu;
    private String connection;
    private String boostClock;
    private Integer cudaCores;
    private String memorySpeed;
    private String memory;
    private String memoryBus;
    private String displayPorts;
    private Boolean hdcpSupport;
    private Boolean powerConnectors;
    private Integer recommendedPSU;
    private String dimensions;
    private String directXSupport;
    private String openGLSupport;
    private Integer multiDisplaySupport;
    private String maxResolution;
}
