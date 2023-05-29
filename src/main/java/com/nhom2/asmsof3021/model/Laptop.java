package com.nhom2.asmsof3021.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Laptop extends Product{
    private String chip;
    private String card;
    private String ram;
    private String hardDisk;
    private String baohanh;
    private String screenSize;
    private String os;
    private String lan;
    private String wireLessLan;
    private String connector;
    private String banPhim;
    private String pin;
    private String weight;
    private String size;
    private String chipDetail;
    private String cardDetail;
    private String ramDetail;
    private String hardDiskDetail;
    private String baohanhDetail;
    private String screenSizeDetail;

    public Laptop() {

    }
}
