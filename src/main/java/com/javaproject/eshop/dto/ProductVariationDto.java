package com.javaproject.eshop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariationDto {

    @NotBlank(message = "Color can not be empty")
    @NotNull(message = "Color can not be null")
    private String color;

    @NotBlank(message = "Storage capacity can not be empty")
    @NotNull(message = "Storage capacity can not be null")
    private String storageCapacity;
}
