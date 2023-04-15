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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int voucherId;
    @Id
    private String voucherCode;
    private double value;
    private boolean active;

    @JsonIgnore
    @OneToMany(mappedBy = "voucher")
    private List<Cart> cartList = new ArrayList<>();

    @Builder
    public Voucher(String voucherCode, double value, boolean active) {
        this.voucherCode = voucherCode;
        this.value = value;
        this.active = active;
    }
}
