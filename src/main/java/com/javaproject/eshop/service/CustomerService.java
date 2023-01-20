package com.javaproject.eshop.service;

import com.javaproject.eshop.dto.CustomerDto;
import com.javaproject.eshop.dto.CustomerUpdateDto;
import com.javaproject.eshop.entity.Customer;
import com.javaproject.eshop.exceptions.EmailAlreadyExistsException;
import com.javaproject.eshop.exceptions.UnknownCustomerException;
import com.javaproject.eshop.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Customer saveCustomer(CustomerDto customerDto) {
        Optional<Customer> customer = customerRepository.findCustomerByEmail(customerDto.getEmail());

        if (customer.isPresent()) {
            throw new EmailAlreadyExistsException("This emails already exists");
        }

        Customer toSaveCustomer = Customer.builder()
                .lastName(customerDto.getLastName())
                .firstName(customerDto.getFirstName())
                .email(customerDto.getEmail())
                .build();

        return customerRepository.save(toSaveCustomer);
    }

    public Customer getCustomer(int customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> new UnknownCustomerException("No customer corresponds to this data"));
    }

    public Customer updateCustomer(CustomerUpdateDto customerUpdateDto, int customerId) {
        Optional<Customer> customer = customerRepository.findCustomerByEmailAndCustomerIdNot(customerUpdateDto.getEmail(), customerId);

        if (customer.isPresent()) {
            throw new EmailAlreadyExistsException("This email already exists");
        }

        Customer toUpdateCustomer = Customer.builder()
                .lastName(customerUpdateDto.getLastName())
                .firstName(customerUpdateDto.getFirstName())
                .email(customerUpdateDto.getEmail())
                .build();

        toUpdateCustomer.setCustomerId(customerId);

        return customerRepository.save(toUpdateCustomer);
    }
}
