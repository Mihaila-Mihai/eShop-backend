package com.javaproject.eshop.service;

import com.javaproject.eshop.dto.CustomerDto;
import com.javaproject.eshop.entity.Customer;
import com.javaproject.eshop.exceptions.UnknownCustomerException;
import com.javaproject.eshop.repository.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTests {
    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("Save customer test")
    void saveCustomer() {
        CustomerDto customerDto = new CustomerDto("m.m@m.ro", "Mihai", "Mihaila");

        Customer customer = Customer.builder()
                .firstName("Mihai")
                .lastName("Mihaila")
                .build();

        when(customerRepository.save(customer)).thenReturn(customer);

        Customer result = customerService.saveCustomer(customerDto);

        assertEquals("Mihai", result.getFirstName());
        assertEquals("Mihaila", result.getLastName());
        assertEquals("m.m@m.ro", result.getEmail());
    }

    @Test
    @DisplayName("Get customer success test")
    void getCustomerSuccess() {
        int customerId = 1;
        Customer customer = Customer.builder()
                .firstName("Mihai")
                .lastName("Mihaila")
                .build();
        customer.setCustomerId(1);
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        Customer result = customerService.getCustomer(customerId);

        assertNotNull(result);
        assertEquals(1, customer.getCustomerId());
    }

    @Test
    @DisplayName("Get customer throws test")
    void getCustomerThrows() {
        String expected = "No customer corresponds to this data";

        UnknownCustomerException result = assertThrows(UnknownCustomerException.class,
                () -> customerService.getCustomer(1));

        assertEquals(expected, result.getMessage());
    }
}
