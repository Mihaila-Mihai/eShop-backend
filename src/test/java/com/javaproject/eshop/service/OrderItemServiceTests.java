package com.javaproject.eshop.service;

import com.javaproject.eshop.entity.OrderItem;
import com.javaproject.eshop.repository.OrderItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class OrderItemServiceTests {
    @InjectMocks
    private OrderItemService orderItemService;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Test
    @DisplayName("Save order items")
    void SaveOrderItems() {
        OrderItem orderItem = OrderItem.builder()
                .price(20)
                .quantity(20)
                .displayName("Samsung")
                .build();

        assertDoesNotThrow(() -> orderItemService.saveOrderItems(List.of(orderItem)));
    }
}
