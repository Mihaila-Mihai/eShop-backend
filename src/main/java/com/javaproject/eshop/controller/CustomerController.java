package com.javaproject.eshop.controller;

import com.javaproject.eshop.dto.CustomerDto;
import com.javaproject.eshop.dto.CustomerUpdateDto;
import com.javaproject.eshop.entity.Customer;
import com.javaproject.eshop.exceptions.NotMatchingIdsException;
import com.javaproject.eshop.helpers.AuthCredentialsRequest;
import com.javaproject.eshop.helpers.ErrorResponse;
import com.javaproject.eshop.helpers.OkResponse;
import com.javaproject.eshop.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;

@RestController
@RequestMapping("/eShop")
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final AuthenticationManager authenticationManager;

    private SecurityContextRepository securityContextRepository;

    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthCredentialsRequest request, HttpServletRequest req, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(
                request.getEmail(), request.getPassword()
        );
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContext context = securityContextHolderStrategy.createEmptyContext();
        context.setAuthentication(authentication);
        securityContextHolderStrategy.setContext(context);
        securityContextRepository.saveContext(context, req, response);
        return ResponseEntity.ok().body("");
    }

    @PostMapping("/register")
    public ResponseEntity<Customer> saveCustomer(@RequestBody @Valid CustomerDto customerDto) {
        Customer customer = customerService.saveCustomer(customerDto);
        return ResponseEntity.created(URI.create("/customer/" + customer.getCustomerId())).body(customer);
    }

    public String currentUserName(Principal principal) {
        return principal.getName();
    }

    @GetMapping("/customer")
    public ResponseEntity<?> getCustomer(Principal principal) {
        if (principal != null) {
            return ResponseEntity.ok(new OkResponse(principal.getName()));
        }
        return ResponseEntity.ok().body(new ErrorResponse("not found"));
    }

    @GetMapping("/customer/{email}")
    public ResponseEntity<?> getCustomerByEmail(@PathVariable String email) {
        Customer customer = customerService.getCustomerByEmail(email);
        customer.setPassword(null);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/customer/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@RequestBody @Valid CustomerUpdateDto customerUpdateDto, @PathVariable int customerId) {
        if (customerId != customerUpdateDto.getCustomerId()) {
            throw new NotMatchingIdsException("Ids must match");
        }
        return ResponseEntity.ok(customerService.updateCustomer(customerUpdateDto, customerId));
    }
}
