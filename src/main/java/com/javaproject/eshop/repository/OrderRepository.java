package com.javaproject.eshop.repository;

import com.javaproject.eshop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("select o from Order o where o.customer.customerId = ?1")
    List<Order> findAllByCustomer(int customerId);
}
