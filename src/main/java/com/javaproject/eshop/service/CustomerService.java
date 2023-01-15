package com.javaproject.eshop.service;

import com.javaproject.eshop.dto.CustomerDto;
import com.javaproject.eshop.entity.Customer;
import com.javaproject.eshop.exceptions.UnknownCustomerException;
import com.javaproject.eshop.repository.CustomerRepository;
import lombok.AllArgsConstructor;
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
        return customerRepository.findById(customerId).orElseThrow(() -> new UnknownCustomerException("No customer corresponds to this data"));
    }
}
