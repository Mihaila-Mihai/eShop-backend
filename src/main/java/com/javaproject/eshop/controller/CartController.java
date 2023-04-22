package com.javaproject.eshop.controller;

import com.javaproject.eshop.dto.AddVoucherDto;
import com.javaproject.eshop.entity.Cart;
import com.javaproject.eshop.helpers.AddToCartPayload;
import com.javaproject.eshop.helpers.OkResponse;
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
    public ResponseEntity<OkResponse> deleteCart(@PathVariable int customerId) {
        cartService.deleteCart(customerId);
        return ResponseEntity.ok(new OkResponse("Your cart was deleted"));
    }

    @PostMapping("/cart/voucher")
    public ResponseEntity<OkResponse> applyVoucher(@RequestBody AddVoucherDto addVoucherDto) {
        cartService.applyVoucher(addVoucherDto);
        return ResponseEntity.ok(new OkResponse("Voucher successfully applied"));
    }

    @DeleteMapping("/{cartId}/voucher")
    public ResponseEntity<OkResponse> removeVoucher(@PathVariable int cartId) {
        cartService.removeVoucher(cartId);
        return ResponseEntity.ok(new OkResponse("Voucher successfully removed"));
    }
}
