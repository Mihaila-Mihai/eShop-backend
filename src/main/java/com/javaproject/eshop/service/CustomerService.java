package com.javaproject.eshop.service;

import com.javaproject.eshop.dto.CustomerDto;
import com.javaproject.eshop.dto.CustomerUpdateDto;
import com.javaproject.eshop.entity.Customer;
import com.javaproject.eshop.exceptions.EmailAlreadyExistsException;
import com.javaproject.eshop.exceptions.UnknownCustomerException;
import com.javaproject.eshop.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerService implements UserDetailsService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder pass;

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

        toSaveCustomer.setPassword(pass.encode(customerDto.getPassword()));
        toSaveCustomer.setEnabled(true);

        return customerRepository.save(toSaveCustomer);
    }

    public Customer getCustomer(int customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> new UnknownCustomerException("No customer corresponds to this data"));
    }

    public Customer getCustomerByEmail(String email) {
        return customerRepository.findCustomerByEmail(email).orElseThrow(() -> new UnknownCustomerException("No customer"));
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> user = this.customerRepository.findCustomerByEmail(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return new User(user.get().getUsername(), user.get().getPassword(), user.get().getAuthorities());
    }
}

