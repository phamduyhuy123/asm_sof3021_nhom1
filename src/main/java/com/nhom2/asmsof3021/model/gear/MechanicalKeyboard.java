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
public class MechanicalKeyboard extends Product {
    private String keyboardType;

    private String switchType;
    private String keycap;
    private String nKey;
    private String connectionType;
    private String cableLength;
    private String battery;
    private String backlight;

    private int warranty;
}
