package com.javaproject.eshop.dto;

import com.javaproject.eshop.entity.ProductVariation;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    @NotBlank(message = "Product name can not be empty or null")
    private String displayName;

    private int quantity;

    @NotNull(message = "You have to specify a price")
    @Min(0)
    private double price;

    @NotNull(message = "You have to specify quantity")
    @Min(value = 1, message = "Product quantity should be at least one")
    private int stock;

    private List<ProductVariationDto> variations;
}
