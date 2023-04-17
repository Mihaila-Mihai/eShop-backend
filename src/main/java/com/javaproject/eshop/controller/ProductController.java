package com.javaproject.eshop.controller;

import com.javaproject.eshop.dto.ProductDto;
import com.javaproject.eshop.entity.Product;
import com.javaproject.eshop.helpers.ProductModel;
import com.javaproject.eshop.helpers.ProductModelAssembler;
import com.javaproject.eshop.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/eShop")
public class ProductController {
    private final ProductService productService;
    private final ProductModelAssembler productModelAssembler;
    private final PagedResourcesAssembler<Product> productPagedResourcesAssembler;

    public ProductController(ProductService productService, ProductModelAssembler productModelAssembler, PagedResourcesAssembler<Product> productPagedResourcesAssembler) {
        this.productService = productService;
        this.productModelAssembler = productModelAssembler;
        this.productPagedResourcesAssembler = productPagedResourcesAssembler;
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
    private ResponseEntity<PagedModel<ProductModel>> getProducts(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "1") @Min(1) int size,
            @RequestParam(defaultValue = "") List<String> sortList,
            @RequestParam(defaultValue = "DESC") Sort.Direction sortOrder) {
        Page<Product> products = productService.getPaginatedProducts(page, size, sortList, sortOrder.toString());
        return ResponseEntity.ok(productPagedResourcesAssembler.toModel(products, productModelAssembler));
    }
}
