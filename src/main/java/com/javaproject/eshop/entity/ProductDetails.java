package com.javaproject.eshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
public class ProductDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productVariationId;

    @JsonIgnore
    @OneToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "productId")
    private Product product;

    private String color;
    private String storageCapacity;
    private String brand;
    private String otherColors;
    private double rating;


    @Builder
    public ProductDetails(Product product, String color, String storageCapacity, String brand, String otherColors, double rating) {
        this.product = product;
        this.color = color;
        this.storageCapacity = storageCapacity;
        this.brand = brand;
        this.otherColors = otherColors;
        this.rating = rating;
    }
}

