package com.javaproject.eshop.controller;

import com.javaproject.eshop.entity.Order;
import com.javaproject.eshop.helpers.OkResponse;
import com.javaproject.eshop.helpers.OrdersResponse;
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
    public ResponseEntity<OkResponse> checkoutCart(@PathVariable int customerId) {
        orderService.checkout(customerId);
        return ResponseEntity.ok(new OkResponse("Order successfully placed"));
    }

    @GetMapping("/{customerId}/order-history")
    public ResponseEntity<OrdersResponse> getOrders(@PathVariable int customerId) {
        List<Order> orders = orderService.getOrders(customerId);

        return ResponseEntity.ok(new OrdersResponse(orders));
    }
}
