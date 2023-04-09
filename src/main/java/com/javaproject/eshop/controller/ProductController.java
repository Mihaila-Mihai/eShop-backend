package com.javaproject.eshop.controller;

import com.javaproject.eshop.dto.ProductDto;
import com.javaproject.eshop.entity.Product;
import com.javaproject.eshop.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/eShop")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product")
    private ResponseEntity<Product> saveProduct(@RequestBody @Valid ProductDto productDto) {
        Product product = productService.saveProduct(productDto);
        return ResponseEntity.created(URI.create("/product/" + product.getProductId())).body(product);
    }

    @GetMapping("/product/{productId}")
    private ResponseEntity<Product> getProduct(@PathVariable int productId) {
        return ResponseEntity.ok(productService.getProduct(productId));
    }

    @GetMapping("/products")
    private ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }
}
