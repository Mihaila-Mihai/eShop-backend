package com.javaproject.eshop.controller;

import com.javaproject.eshop.dto.ProductVariationDto;
import com.javaproject.eshop.entity.ProductVariation;
import com.javaproject.eshop.service.ProductVariationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/eShop")
@AllArgsConstructor
public class ProductVariationController {
    private final ProductVariationService productVariationService;

    @GetMapping("/{productId}/variations")
    private ResponseEntity<List<ProductVariation>> getVariations(@PathVariable int productId) {
        return ResponseEntity.ok(productVariationService.getVariations(productId));
    }

    @PostMapping("/{productId}/variation")
    private ResponseEntity<ProductVariation> addVariation(@PathVariable int productId, @RequestBody ProductVariationDto productVariationDto) {
        ProductVariation productVariation = productVariationService.saveProductVariation(productVariationDto, productId);
        return ResponseEntity.created(URI.create("/" + productId + "/variation/" + productVariation.getProductVariationId())).body(productVariation);
    }
}
