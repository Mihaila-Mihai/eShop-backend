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

    @NotBlank(message = "Product name can not be empty")
    @NotNull(message = "Product name can not be null")
    private String displayName;

    @NotNull(message = "You have to specify a price")
    @Min(value = 1, message = "Price can not be less that 1")
    private double price;

    @NotNull(message = "You have to specify quantity")
    @Min(value = 1, message = "Product quantity should be at least one")
    private int stock;

    private List<ProductVariationDto> variations;
}
