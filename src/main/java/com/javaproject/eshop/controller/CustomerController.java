package com.javaproject.eshop.controller;

import com.javaproject.eshop.dto.CustomerDto;
import com.javaproject.eshop.dto.CustomerUpdateDto;
import com.javaproject.eshop.entity.Customer;
import com.javaproject.eshop.exceptions.NotMatchingIdsException;
import com.javaproject.eshop.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/eShop")
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<Customer> saveCustomer(@RequestBody @Valid CustomerDto customerDto) {
        Customer customer = customerService.saveCustomer(customerDto);
        return ResponseEntity.created(URI.create("/customer/" + customer.getCustomerId())).body(customer);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Customer> getCustomer(@PathVariable int customerId) {
        return ResponseEntity.ok(customerService.getCustomer(customerId));
    }

    @PutMapping("/customer/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@RequestBody @Valid CustomerUpdateDto customerUpdateDto, @PathVariable int customerId) {
        if (customerId != customerUpdateDto.getCustomerId()) {
            throw new NotMatchingIdsException("Ids must match");
        }
        return ResponseEntity.ok(customerService.updateCustomer(customerUpdateDto, customerId));
    }
}
