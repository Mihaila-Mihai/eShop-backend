package com.javaproject.eshop.service;

import com.javaproject.eshop.dto.ProductDto;
import com.javaproject.eshop.dto.ProductVariationDto;
import com.javaproject.eshop.entity.Product;
import com.javaproject.eshop.entity.ProductVariation;
import com.javaproject.eshop.repository.ProductRepository;
import com.javaproject.eshop.repository.ProductVariationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ProductVariationServiceTests {

    @InjectMocks
    private ProductVariationService productVariationService;

    @Mock
    private ProductService productService;

    @Mock
    private ProductVariationRepository productVariationRepository;

    @Mock
    private ProductRepository productRepository;

    private Product product;


    @BeforeEach
    void setup() {
        product = Product.builder()
                .displayName("Samsung")
                .stock(20)
                .price(20)
                .build();
        product.setProductId(1);
    }


    @Test
    @DisplayName("Save product variation test")
    void saveProductVariationSuccess() {
        ProductVariationDto productVariationDto = new ProductVariationDto("blue", "128GB");

        ProductVariation productVariation = ProductVariation.builder()
                .product(product)
                .color(productVariationDto.getColor())
                .storageCapacity(productVariationDto.getStorageCapacity())
                .build();

        doReturn(product).when(productService).getProduct(product.getProductId());
        doReturn(productVariation).when(productVariationRepository).save(productVariation);

        ProductVariation result = productVariationService.saveProductVariation(productVariationDto, product.getProductId());

        assertEquals("blue", result.getColor());
        assertEquals("128GB", result.getStorageCapacity());
    }

    @Test
    @DisplayName("Get product variations test")
    void getProducts() {
        int productId = 1;
        ProductVariationDto productVariationDto = new ProductVariationDto("blue", "128GB");

        ProductVariation productVariation = ProductVariation.builder()
                .product(product)
                .color(productVariationDto.getColor())
                .storageCapacity(productVariationDto.getStorageCapacity())
                .build();

        doReturn(product).when(productService).getProduct(productId);
        doReturn(List.of(productVariation)).when(productVariationRepository).findProductVariations(productId);

        List<ProductVariation> result = productVariationService.getVariations(productId);

        assertEquals(1, result.size());
    }
}
