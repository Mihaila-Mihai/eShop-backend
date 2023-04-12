package com.javaproject.eshop.controller;

import com.javaproject.eshop.entity.Cart;
import com.javaproject.eshop.helpers.AddToCartPayload;
import com.javaproject.eshop.helpers.CartAction;
import com.javaproject.eshop.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eShop")
@AllArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("/{customerId}/cart")
    public ResponseEntity<Cart> getCart(@PathVariable int customerId) {
        return ResponseEntity.ok(cartService.getCart(customerId));
    }

    @PostMapping("/cart")
    public ResponseEntity<?> addToCart(@RequestBody AddToCartPayload payload) { // todo - rename in dto
        cartService.addToCart(payload.getProductId(), payload.getCustomerId());
        return ResponseEntity.ok("");
    }

    @DeleteMapping("/{customerId}/cart")
    public ResponseEntity<String> deleteCart(@PathVariable int customerId) {
        cartService.deleteCart(customerId);
        return ResponseEntity.ok("Your cart was deleted");
    }

    @PostMapping("/{customerId}/cart/voucher/{voucherId}")
    public ResponseEntity<String> addVoucher(@PathVariable(value = "customerId") int customerId, @PathVariable(value = "voucherId") int voucherId) {
        cartService.addVoucher(voucherId, customerId);
        return ResponseEntity.ok("Voucher successfully applied");
    }
}
