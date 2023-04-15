package com.javaproject.eshop.dto;

public class AddVoucherDto {
    private int customerId;
    private String voucherCode;

    public AddVoucherDto(int customerId, String voucherCode) {
        this.customerId = customerId;
        this.voucherCode = voucherCode;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }
}
