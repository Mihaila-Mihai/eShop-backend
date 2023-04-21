package com.javaproject.eshop.service;

import com.javaproject.eshop.dto.AddVoucherDto;
import com.javaproject.eshop.entity.Cart;
import com.javaproject.eshop.entity.Customer;
import com.javaproject.eshop.entity.Product;
import com.javaproject.eshop.entity.Voucher;
import com.javaproject.eshop.exceptions.EmptyCartException;
import com.javaproject.eshop.exceptions.InvalidVoucherException;
import com.javaproject.eshop.exceptions.OutOfStockException;
import com.javaproject.eshop.repository.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceTests {
    @InjectMocks
    private CartService cartService;

    @Mock
    private CartRepository cartRepository;
    @Mock
    private CustomerService customerService;
    @Mock
    private ProductService productService;
    @Mock
    private VoucherService voucherService;


    private Voucher voucher;
    private Cart cart;
    private List<Product> products;
    private Customer customer;
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
    @DisplayName("Save cart test")
    void saveCart() {
        int customerId = 1;

        when(customerService.getCustomer(customerId)).thenReturn(customer);
        when(cartRepository.save(cart)).thenReturn(cart);

        assertDoesNotThrow(() -> cartService.saveCart(cart, customerId));
    }

    @Test
    @DisplayName("Get cart success test")
    void getCartSuccess() {
        int customerId = 1;
        customer.setCart(cart);

        when(customerService.getCustomer(customerId)).thenReturn(this.customer);

        Cart result = cartService.getCart(customerId);

        assertEquals(20, result.getTotalPrice());
    }

    @Test
    @DisplayName("Get cart throw test")
    void getCartThrow() {
        String expected = "Your cart is empty";
        int customerId = 1;

        when(customerService.getCustomer(customerId)).thenReturn(this.customer);

        EmptyCartException result = assertThrows(EmptyCartException.class,
                () -> cartService.getCart(1));

        assertEquals(expected, result.getMessage());
    }

    @Test
    @DisplayName("Delete cart success test")
    void deleteCartSuccess() {
        int customerId = 1;
        customer.setCart(cart);

        when(customerService.getCustomer(customerId)).thenReturn(this.customer);

        assertDoesNotThrow(() -> cartService.deleteCart(customerId));
    }

    @Test
    @DisplayName("Delete cart throw test")
    void deleteCartThrow() {
        int customerId = 1;
        String expected = "Cart is empty";
        customer.setCart(cart);

        doReturn(this.customer).when(customerService).getCustomer(customerId);

        customer.setCart(null);

        EmptyCartException result = assertThrows(EmptyCartException.class,
                () -> cartService.deleteCart(customerId));
    }

    @Test
    @DisplayName("Add to cart success test")
    void addToCart() {
        int productId = 1;
        int customerId = 1;
        customer.setCart(cart);

        doReturn(this.customer).when(customerService).getCustomer(customerId);
        doReturn(this.product).when(productService).getProduct(productId);

        assertDoesNotThrow(() -> cartService.addToCart(productId, customerId));
        Cart result = cartService.getCart(customerId);
        assertEquals(40, result.getTotalPrice());
    }

    @Test
    @DisplayName("Add to cart throw test")
    void addToCartThrow() {
        int productId = 1;
        int customerId = 1;
        customer.setCart(cart);
        String expected = "Product out of stock";

        doReturn(this.customer).when(customerService).getCustomer(customerId);
        doReturn(this.product.setStock(0)).when(productService).getProduct(productId);

        OutOfStockException result = assertThrows(OutOfStockException.class,
                () -> cartService.addToCart(productId, customerId));

        assertEquals(expected, result.getMessage());
    }

    @Test
    @DisplayName("Add to empty cart test")
    void addToEmptyCart() {
        int productId = 1;
        int customerId = 1;
        customer.setCart(null);

        doReturn(this.customer).when(customerService).getCustomer(customerId);
        doReturn(this.product).when(productService).getProduct(productId);

        assertDoesNotThrow(() -> cartService.addToCart(productId, customerId));
        Cart result = cartService.getCart(customerId);
        assertEquals(20, result.getTotalPrice());
    }

    @Test
    @DisplayName("Add voucher test")
    void addVoucher() {
        int voucherId = 1;
        String voucherCode = "1";
        int customerId = 1;
        customer.setCart(cart);
        AddVoucherDto addVoucherDto = new AddVoucherDto(customerId,voucherCode);

        doReturn(this.customer).when(customerService).getCustomer(customerId);
        doReturn(this.voucher).when(voucherService).getVoucher(voucherCode);

        assertDoesNotThrow(() -> cartService.applyVoucher(addVoucherDto));
        Cart result = cartService.getCart(customerId);
        assertEquals(0, cart.getTotalPrice());
    }

    @Test
    @DisplayName("Add voucher throws test")
    void addVoucherThrows() {
        int voucherId = 1;
        String voucherCode = "1";
        int customerId = 1;
        customer.setCart(cart);
        this.voucher.setActive(false);
        String expected = "Voucher is not valid";
        AddVoucherDto addVoucherDto = new AddVoucherDto(customerId,voucherCode);

        doReturn(this.customer).when(customerService).getCustomer(customerId);
        doReturn(this.voucher).when(voucherService).getVoucher(voucherCode);

        InvalidVoucherException result = assertThrows(InvalidVoucherException.class,
                () -> cartService.applyVoucher(addVoucherDto));
        assertEquals(expected, result.getMessage());
    }
}
