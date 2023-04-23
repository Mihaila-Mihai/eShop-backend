package com.javaproject.eshop.controller;


import com.javaproject.eshop.entity.Cart;
import com.javaproject.eshop.entity.Customer;
import com.javaproject.eshop.entity.Product;
import com.javaproject.eshop.entity.Voucher;
import com.javaproject.eshop.repository.CartRepository;
import com.javaproject.eshop.service.CartService;
import com.javaproject.eshop.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CartControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CustomerService customerService;
    @MockBean
    CartRepository cartRepository;
    @MockBean
    CartService cartService;

    private Voucher voucher;
    private Customer customer;
    private List<Product> products;
    private Cart cart;
    private Product product;

    @BeforeEach
    void setup() {
        this.voucher = Voucher.builder()
                .value(20)
                .voucherCode("SUMMER-20")
                .active(true)
                .build();
        this.products = List.of(
                Product.builder()
                        .displayName("Samsung")
                        .price(20)
                        .stock(10)
                        .build()
        );
        this.customer = Customer.builder()
                .firstName("Mihai")
                .lastName("Mihaila")
                .build();
        this.cart = new Cart(1, 20, null, products, customer);

        this.product = Product.builder()
                .displayName("Samsung")
                .price(20)
                .stock(10)
                .build();
    }

    @Test
    public void getCart() throws Exception {
        int customerId = 1;

        when(customerService.getCustomer(customerId)).thenReturn(customer);
        cartService.saveCart(cart, customerId);


        this.mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8050/eShop/{customerId}/cart", "1"))
                .andExpect(status().isOk());
    }

}
