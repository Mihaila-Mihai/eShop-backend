package com.javaproject.eshop.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoucherDto {

    @NotBlank(message = "Name can not be empty")
    @NotNull(message = "Name can not be null")
    private String voucherCode;

    @Min(value = 1, message = "Voucher value should be at least one")
    private double value;

    @NotNull(message = "You have to specify the voucher availability")
    private Boolean isActive;
}
