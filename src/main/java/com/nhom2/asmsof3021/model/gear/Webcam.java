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
public class Webcam extends Product {
    private String brand;
    private String resolution;
    private boolean autofocus;
    private boolean builtInMicrophone;
}
