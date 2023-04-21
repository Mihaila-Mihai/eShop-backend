package com.javaproject.eshop.controller;

import com.javaproject.eshop.dto.CustomerUpdateDto;
import com.javaproject.eshop.entity.Customer;
import com.javaproject.eshop.exceptions.NotMatchingIdsException;
import com.javaproject.eshop.service.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class CustomerControllerTests {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @Test
    @DisplayName("Update customer throw test")
    void updateCustomerThrow() {
        int customerId = 1;
        CustomerUpdateDto customerUpdateDto = new CustomerUpdateDto(2, "m.m@m.ro", "Mihai", "Mihaila");

        assertThrows(NotMatchingIdsException.class,
                () -> customerController.updateCustomer(customerUpdateDto, customerId));
    }

    @Test
    @DisplayName("Update customer test")
    void updateCustomer() {
        int customerId = 2;
        CustomerUpdateDto customerUpdateDto = new CustomerUpdateDto(2, "m.m@m.ro", "Mihai", "Mihaila");
        Customer customer = Customer.builder()
                    .email(customerUpdateDto.getEmail())
                    .firstName(customerUpdateDto.getFirstName())
                    .lastName(customerUpdateDto.getLastName())
                    .build();
        customer.setCustomerId(customerId);

        doReturn(customer).when(customerService).updateCustomer(customerUpdateDto, customerId);
        Customer result = customerService.updateCustomer(customerUpdateDto, customerId);

        assertDoesNotThrow(() -> customerController.updateCustomer(customerUpdateDto, customerId));
        assertEquals(2, result.getCustomerId());
    }
}
