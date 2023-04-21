package com.javaproject.eshop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailsDto {

    @NotBlank(message = "Color can not be empty")
    @NotNull(message = "Color can not be null")
    private String color;

    @NotBlank(message = "Storage capacity can not be empty")
    @NotNull(message = "Storage capacity can not be null")
    private String storageCapacity;

    private String brand;
    private String otherColors;
    private double rating;

    @Builder
    public ProductDetailsDto(String color, String storageCapacity, String brand) {
        this.color = color;
        this.storageCapacity = storageCapacity;
        this.brand = brand;
        this.otherColors = otherColors;
        this.rating = rating;
    }
}
