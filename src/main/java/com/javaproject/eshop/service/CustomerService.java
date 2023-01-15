package com.javaproject.eshop.service;

import com.javaproject.eshop.dto.CustomerDto;
import com.javaproject.eshop.entity.Customer;
import com.javaproject.eshop.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Customer saveCustomer(CustomerDto customerDto) {
        Customer customer = Customer.builder()
                .lastName(customerDto.getLastName())
                .firstName(customerDto.getFirstName())
                .build();

        return customerRepository.save(customer);
    }

    public Customer getCustomer(int customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("No user found"));
    }
}
