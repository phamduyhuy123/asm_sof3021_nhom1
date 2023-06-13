package com.nhom2.asmsof3021.model;

import com.nhom2.asmsof3021.validation.annotations.PhoneNumber;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    private User user;
    @NotBlank(message = "Địa chỉ giao hàng đang để trống")
    private String address;
    @NotBlank(message = "Email đang để trống")
    @Email(message = "Email không đúng định dạng")
    private String email;
    @NotBlank(message = "Username đang để trống")
    private String username;

    @NotBlank(message = "Số điện thoại đang để trống")
    private String phoneNumber;
    private BigDecimal totalPrice;
    @Temporal(TemporalType.DATE)
    private Date createAt;
    @OneToMany(mappedBy = "orders",cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private List<OrdersDetail> ordersDetails;
}
