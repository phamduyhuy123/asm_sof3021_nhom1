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
public class Microphone extends Product {
    private String connectivity;
    private boolean wireless;
    private String frequencyResponse;
    private boolean noiseCancelling;
}
