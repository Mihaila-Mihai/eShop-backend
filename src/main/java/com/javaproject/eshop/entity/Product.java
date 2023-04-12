package com.javaproject.eshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;
    private String displayName;
    private double price;
    private int stock;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private ProductDetails details;

    @JsonIgnore
    @ManyToMany(mappedBy = "products")
    private List<Cart> carts = new ArrayList<>();

    @Builder
    public Product(String displayName, double price, int stock) {
        this.displayName = displayName;
        this.price = price;
        this.stock = stock;
    }

    public Product setStock(int stock) {
        this.stock = stock;
        return this;
    }
}
