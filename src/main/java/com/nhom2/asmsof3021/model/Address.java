package com.nhom2.asmsof3021.model;

import com.nhom2.asmsof3021.security.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Address {
    @Id
    private Integer id;
    private String addressDetail;
    @ManyToOne
    private User user;
}
