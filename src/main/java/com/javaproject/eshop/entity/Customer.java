package com.javaproject.eshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;

    private String email;
    private String firstName;
    private String lastName;

    @JsonIgnore
    @OneToOne(mappedBy = "customer")
    private Cart cart;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    @Builder
    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
