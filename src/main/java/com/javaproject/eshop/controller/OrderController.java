package com.javaproject.eshop.controller;

import com.javaproject.eshop.entity.Order;
import com.javaproject.eshop.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eShop")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/{customerId}/checkout")
    public ResponseEntity<String> checkoutCart(@PathVariable int customerId) {
        orderService.checkout(customerId);
        return ResponseEntity.ok("Order successfully placed");
    }

    @GetMapping("{customerId}/order-history")
    public ResponseEntity<List<Order>> getOrders(@PathVariable int customerId) {
        return ResponseEntity.ok(orderService.getOrders(customerId));
    }
}
