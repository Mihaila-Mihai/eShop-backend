package com.javaproject.eshop.service;

import com.javaproject.eshop.dto.ProductDto;
import com.javaproject.eshop.dto.ProductVariationDto;
import com.javaproject.eshop.entity.*;
import com.javaproject.eshop.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)
public class OrderServiceTests {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private CartService cartService;

    @Mock
    private CustomerService customerService;

    @Mock
    private OrderItemService orderItemService;

    @Mock
    private OrderRepository orderRepository;

    @Test
    @DisplayName("Checkout test")
    void checkout() {
        ProductVariationDto productVariationDto = new ProductVariationDto("blue", "128GB");
        ProductDto productDto = new ProductDto("Samsung", 20, 20, List.of(productVariationDto));
        int customerId = 1;
        Customer customer = Customer.builder()
                .email("m.m@m.ro")
                .firstName("Mihai")
                .lastName("Mihaila")
                .build();
        Voucher voucher = Voucher.builder()
                .active(true)
                .value(20)
                .name("SUMMER-20")
                .build();
        Product product = Product.builder()
                .displayName(productDto.getDisplayName())
                .price(productDto.getPrice())
                .stock(productDto.getStock())
                .build();
        Cart cart = Cart.builder()
                .customer(customer)
                .voucher(voucher)
                .totalPrice(20)
                .products(List.of(product))
                .build();
        cart.setCartId(1);
        customer.setCart(cart);

        doReturn(customer).when(customerService).getCustomer(customerId);
        doReturn(cart).when(cartService).getCart(customerId);

        Order order = Order.builder()
                .createdOn(Date.valueOf(LocalDate.now()))
                .customer(customer)
                .build();

        doReturn(order).when(orderRepository).save(order);

        Order savedOrder = orderRepository.save(order);

        List<OrderItem> orderItems = cart.getProducts().stream().map(
                productItem -> OrderItem.builder()
                        .price(productItem.getPrice())
                        .displayName(productItem.getDisplayName())
                        .order(order)
                        .build()
        ).toList();

        assertDoesNotThrow(() -> orderService.checkout(customerId));
    }

    @Test
    @DisplayName("Get orders test")
    void getOrders() {
        ProductVariationDto productVariationDto = new ProductVariationDto("blue", "128GB");
        ProductDto productDto = new ProductDto("Samsung", 20, 20, List.of(productVariationDto));
        int customerId = 1;
        Customer customer = Customer.builder()
                .email("m.m@m.ro")
                .firstName("Mihai")
                .lastName("Mihaila")
                .build();
        Voucher voucher = Voucher.builder()
                .active(true)
                .value(20)
                .name("SUMMER-20")
                .build();
        Product product = Product.builder()
                .displayName(productDto.getDisplayName())
                .price(productDto.getPrice())
                .stock(productDto.getStock())
                .build();
        Cart cart = Cart.builder()
                .customer(customer)
                .voucher(voucher)
                .totalPrice(20)
                .products(List.of(product))
                .build();
        cart.setCartId(1);
        customer.setCart(cart);

        Order order = Order.builder()
                .createdOn(Date.valueOf(LocalDate.now()))
                .customer(customer)
                .build();

        doReturn(List.of(order)).when(orderRepository).findAllByCustomer_CustomerId(customerId);

        List<Order> result = orderService.getOrders(customerId);

        assertEquals(1, result.size());
    }
}
