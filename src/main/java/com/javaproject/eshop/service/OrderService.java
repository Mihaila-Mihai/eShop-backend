package com.javaproject.eshop.service;

import com.javaproject.eshop.entity.Cart;
import com.javaproject.eshop.entity.Customer;
import com.javaproject.eshop.entity.Order;
import com.javaproject.eshop.entity.OrderItem;
import com.javaproject.eshop.exceptions.EmptyCartException;
import com.javaproject.eshop.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final CustomerService customerService;
    private final OrderItemService orderItemService;

    @Transactional
    public void checkout(int customerId) {
        Customer customer = customerService.getCustomer(customerId);
        Cart cart = cartService.getCart(customerId);

        Order order = Order.builder()
                .createdOn(Date.valueOf(LocalDate.now()))
                .customer(customer)
                .build();

        order = orderRepository.save(order);

        Order finalOrder = order;
        List<OrderItem> orderItems = cart.getProducts().stream().map(
                product -> OrderItem.builder()
                        .price(product.getPrice())
                        .displayName(product.getDisplayName())
                        .order(finalOrder)
                        .build()
        ).toList();


        orderItemService.saveOrderItems(orderItems);
        cartService.deleteCart(customerId);
    }

    public List<Order> getOrders(int customerId) {
        return orderRepository.findAllByCustomer_CustomerId(customerId);
    }
}
