package com.javaproject.eshop.service;

import com.javaproject.eshop.dto.ProductDetailsDto;
import com.javaproject.eshop.entity.Product;
import com.javaproject.eshop.entity.ProductDetails;
import com.javaproject.eshop.repository.ProductDetailsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductDetailsService {
    private final ProductDetailsRepository productDetailsRepository;
    private final ProductService productService;

    public ProductDetails saveProductVariation(ProductDetailsDto productDetailsDto, int productId) {
        Product product = productService.getProduct(productId);

        ProductDetails productDetails = ProductDetails.builder()
                .product(product)
                .color(productDetailsDto.getColor())
                .storageCapacity(productDetailsDto.getStorageCapacity())
                .build();

        return productDetailsRepository.save(productDetails);
    }

    public List<ProductDetails> getVariations(int productId) {
        Product product = productService.getProduct(productId);
        return productDetailsRepository.findProductVariations(productId);
    }
}
