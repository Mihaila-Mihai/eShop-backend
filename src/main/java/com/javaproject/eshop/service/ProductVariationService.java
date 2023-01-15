package com.javaproject.eshop.service;

import com.javaproject.eshop.dto.ProductDto;
import com.javaproject.eshop.dto.ProductVariationDto;
import com.javaproject.eshop.entity.Product;
import com.javaproject.eshop.entity.ProductVariation;
import com.javaproject.eshop.repository.ProductVariationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductVariationService {
    private final ProductVariationRepository productVariationRepository;
    private final ProductService productService;

    public ProductVariation saveProductVariation(ProductVariationDto productVariationDto, int productId) {
        Product product = productService.getProduct(productId);

        ProductVariation productVariation = ProductVariation.builder()
                .product(product)
                .color(productVariationDto.getColor())
                .storageCapacity(productVariationDto.getStorageCapacity())
                .build();

        return productVariationRepository.save(productVariation);
    }

    public List<ProductVariation> getVariations(int productId) {
        Product product = productService.getProduct(productId);
        return productVariationRepository.findProductVariations(productId);
    }
}
