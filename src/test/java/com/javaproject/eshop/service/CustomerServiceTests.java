package com.javaproject.eshop.service;

import com.javaproject.eshop.dto.CustomerDto;
import com.javaproject.eshop.dto.CustomerUpdateDto;
import com.javaproject.eshop.entity.Customer;
import com.javaproject.eshop.exceptions.EmailAlreadyExistsException;
import com.javaproject.eshop.exceptions.UnknownCustomerException;
import com.javaproject.eshop.repository.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTests {
    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PasswordEncoder pass;

    @Test
    @DisplayName("Save customer test")
    //TODO: Fix test
    void saveCustomer() {
        CustomerDto customerDto = CustomerDto.builder().email("m.m@m.ro").firstName("Mihai").lastName("Mihaila").build();

        Customer customer = Customer.builder()
                .email("m.m@m.ro")
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
    @DisplayName("Save customer throw test")
    void saveCustomerThrows() {
        String expected = "This emails already exists";
        CustomerDto customerDto = CustomerDto.builder().email("m.m@m.ro").firstName("Mihai").lastName("Mihaila").build();

        Customer customer = Customer.builder()
                .email("m.m@m.ro")
                .firstName("Mihai")
                .lastName("Mihaila")
                .build();

        when(customerRepository.findCustomerByEmail(customerDto.getEmail())).thenReturn(Optional.ofNullable(customer));

        EmailAlreadyExistsException result = assertThrows(EmailAlreadyExistsException.class,
                () -> customerService.saveCustomer(customerDto));

        assertEquals(expected, result.getMessage());
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

    @Test
    @DisplayName("Update customer test")
    void updateCustomerSuccess() {
        int customerId = 1;
        CustomerUpdateDto customerUpdateDto = new CustomerUpdateDto(1, "m.m@m.ro", "Mihai", "Mihaila");

        Customer toUpdateCustomer = Customer.builder()
                .lastName(customerUpdateDto.getLastName())
                .firstName(customerUpdateDto.getFirstName())
                .email(customerUpdateDto.getEmail())
                .build();
        toUpdateCustomer.setCustomerId(customerId);

        when(customerRepository.save(toUpdateCustomer)).thenReturn(toUpdateCustomer);

        Customer result = customerService.updateCustomer(customerUpdateDto, customerId);

        assertEquals(1, result.getCustomerId());
        assertEquals("m.m@m.ro", result.getEmail());
        assertEquals("Mihai", result.getFirstName());
        assertEquals("Mihaila", result.getLastName());
    }

    @Test
    @DisplayName("Update customer throw test")
    void updateCustomerThrow() {
        String expected = "This email already exists";
        int customerId = 1;
        CustomerUpdateDto customerUpdateDto = new CustomerUpdateDto(1, "m.m@m.ro", "Mihai", "Mihaila");

        Customer toUpdateCustomer = Customer.builder()
                .lastName(customerUpdateDto.getLastName())
                .firstName(customerUpdateDto.getFirstName())
                .email(customerUpdateDto.getEmail())
                .build();
        toUpdateCustomer.setCustomerId(customerId);

        when(customerRepository.findCustomerByEmailAndCustomerIdNot(customerUpdateDto.getEmail(), customerId)).thenReturn(Optional.of(toUpdateCustomer));

        EmailAlreadyExistsException result = assertThrows(EmailAlreadyExistsException.class,
                () -> customerService.updateCustomer(customerUpdateDto, customerId));

        assertEquals(expected, result.getMessage());
    }
}
