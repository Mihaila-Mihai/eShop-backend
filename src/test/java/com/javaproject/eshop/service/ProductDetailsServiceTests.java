package com.javaproject.eshop.service;

import com.javaproject.eshop.dto.ProductDetailsDto;
import com.javaproject.eshop.entity.Product;
import com.javaproject.eshop.entity.ProductDetails;
import com.javaproject.eshop.repository.ProductRepository;
import com.javaproject.eshop.repository.ProductDetailsRepository;
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
public class ProductDetailsServiceTests {

    @InjectMocks
    private ProductDetailsService productDetailsService;

    @Mock
    private ProductService productService;

    @Mock
    private ProductDetailsRepository productDetailsRepository;

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
        ProductDetailsDto productDetailsDto = ProductDetailsDto.builder().color("blue").storageCapacity("128GB").build();

        ProductDetails productDetails = ProductDetails.builder()
                .product(product)
                .color(productDetailsDto.getColor())
                .storageCapacity(productDetailsDto.getStorageCapacity())
                .build();

        doReturn(product).when(productService).getProduct(product.getProductId());
        doReturn(productDetails).when(productDetailsRepository).save(productDetails);

        ProductDetails result = productDetailsService.saveProductVariation(productDetailsDto, product.getProductId());

        assertEquals("blue", result.getColor());
        assertEquals("128GB", result.getStorageCapacity());
    }

    @Test
    @DisplayName("Get product variations test")
    void getProducts() {
        int productId = 1;
        ProductDetailsDto productDetailsDto = ProductDetailsDto.builder().color("blue").storageCapacity("128GB").build();

        ProductDetails productDetails = ProductDetails.builder()
                .product(product)
                .color(productDetailsDto.getColor())
                .storageCapacity(productDetailsDto.getStorageCapacity())
                .build();

        doReturn(product).when(productService).getProduct(productId);
        doReturn(List.of(productDetails)).when(productDetailsRepository).findProductVariations(productId);

        List<ProductDetails> result = productDetailsService.getVariations(productId);

        assertEquals(1, result.size());
    }
}
