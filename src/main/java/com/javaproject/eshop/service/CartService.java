package com.javaproject.eshop.service;

import com.javaproject.eshop.entity.Cart;
import com.javaproject.eshop.entity.Customer;
import com.javaproject.eshop.entity.Product;
import com.javaproject.eshop.entity.Voucher;
import com.javaproject.eshop.exceptions.EmptyCartException;
import com.javaproject.eshop.exceptions.InvalidVoucherException;
import com.javaproject.eshop.exceptions.OutOfStockException;
import com.javaproject.eshop.repository.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CustomerService customerService;
    private final ProductService productService;
    private final VoucherService voucherService;

    public void saveCart(Cart cart, int customerId) {
        Customer customer = customerService.getCustomer(customerId);

        cart.setCustomer(customer);

        cartRepository.save(cart);
    }

    public Cart getCart(int customerId) {
        Cart cart = customerService.getCustomer(customerId).getCart();
        if (cart == null) {
            throw new EmptyCartException("Your cart is empty");
        }
        return cart;
    }

    public void deleteCart(int customerId) {
        Customer customer = customerService.getCustomer(customerId);
        Cart cart = customer.getCart();

        if (cart == null) {
            throw new EmptyCartException("Cart is empty");
        }

        cartRepository.delete(cart);
    }

    @Transactional
    public void addToCart(int productId, int customerId) {
        Customer customer = customerService.getCustomer(customerId);
        Product product = productService.getProduct(productId);

        if (product.getStock() <= 0) {
            throw new OutOfStockException("Product out of stock");
        }

        Cart cart = customer.getCart();

        if (cart == null) {
            cart = new Cart();
        }

        cart.addToCart(product);

        productService.updateStock(productId, product.getStock() - 1);

        saveCart(cart, customerId);
    }

    public void addVoucher(int voucherId, int customerId) {
        Customer customer = customerService.getCustomer(customerId);
        Voucher voucher = voucherService.getVoucher(voucherId);

        if (!voucher.isActive()) {
            throw new InvalidVoucherException("Voucher is not valid");
        }

        Cart cart = getCart(customerId);

        cart.addVoucher(voucher);

        saveCart(cart, customerId);
    }


}
