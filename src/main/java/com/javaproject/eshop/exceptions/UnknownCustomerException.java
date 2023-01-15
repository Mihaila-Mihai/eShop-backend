package com.javaproject.eshop.exceptions;

public class UnknownCustomerException extends RuntimeException {
    public UnknownCustomerException(String message) {
        super(message);
    }
}
