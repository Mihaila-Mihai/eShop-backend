package com.javaproject.eshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int voucherId;

    private String name;
    private double value;
    private boolean active;

    @JsonIgnore
    @OneToMany(mappedBy = "voucher")
    private List<Cart> cartList = new ArrayList<>();

    @Builder
    public Voucher(String name, double value, boolean active) {
        this.name = name;
        this.value = value;
        this.active = active;
    }
}
