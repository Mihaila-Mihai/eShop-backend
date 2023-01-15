package com.javaproject.eshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
public class ProductVariation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productVariationId;

    @JsonIgnore
    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "productId")
    private Product product;

    private String color;
    private String storageCapacity;

    @Builder
    public ProductVariation(Product product, String color, String storageCapacity) {
        this.product = product;
        this.color = color;
        this.storageCapacity = storageCapacity;
    }
}

