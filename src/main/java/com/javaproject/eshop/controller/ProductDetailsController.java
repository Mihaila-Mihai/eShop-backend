package com.javaproject.eshop.controller;

import com.javaproject.eshop.dto.ProductDetailsDto;
import com.javaproject.eshop.entity.ProductDetails;
import com.javaproject.eshop.service.ProductDetailsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/eShop")
@AllArgsConstructor
public class ProductDetailsController {
    private final ProductDetailsService productDetailsService;

    @GetMapping("/{productId}/variations")
    private ResponseEntity<List<ProductDetails>> getVariations(@PathVariable int productId) {
        return ResponseEntity.ok(productDetailsService.getVariations(productId));
    }

    @PostMapping("/{productId}/variation")
    private ResponseEntity<ProductDetails> addVariation(@PathVariable int productId, @RequestBody @Valid ProductDetailsDto productDetailsDto) {
        ProductDetails productDetails = productDetailsService.saveProductVariation(productDetailsDto, productId);
        return ResponseEntity.created(URI.create("/" + productId + "/variation/" + productDetails.getProductVariationId())).body(productDetails);
    }
}
