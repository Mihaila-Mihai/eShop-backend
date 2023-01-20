package com.javaproject.eshop.exceptionhandling;

import com.javaproject.eshop.exceptions.*;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            OutOfStockException.class,
            EmptyCartException.class,
            InvalidVoucherException.class,
            UnknownCustomerException.class,
            UnknownProductException.class,
            EmailAlreadyExistsException.class,
            NotMatchingIdsException.class
    })
    public ResponseEntity<Map<String, String>> handleException(RuntimeException e) {
        Map<String, String> responseParameters = new HashMap<>();
        responseParameters.put("error: ", e.getMessage());
        return ResponseEntity.badRequest().body(responseParameters);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleHibernateValidation(MethodArgumentNotValidException exception) {
        Map<String, Object> responseParameters = new HashMap<>();
        List<String> validationErrorMessages = exception.getBindingResult().getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        responseParameters.put("errors: ", validationErrorMessages);
        return ResponseEntity.badRequest().body(responseParameters);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleDeserializationException(HttpMessageNotReadableException e) {
        Map<String, String> responseParameters = new HashMap<>();
        responseParameters.put("error: ", e.getMessage());
        return ResponseEntity.badRequest().body(responseParameters);
    }

}
