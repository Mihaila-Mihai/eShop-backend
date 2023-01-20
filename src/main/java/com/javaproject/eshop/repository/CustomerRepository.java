package com.javaproject.eshop.repository;

import com.javaproject.eshop.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findCustomerByEmail(String email);
    Optional<Customer> findCustomerByEmailAndCustomerIdNot(String email, int id);
}
