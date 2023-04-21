package com.javaproject.eshop.service;

import com.javaproject.eshop.dto.ProductDto;
import com.javaproject.eshop.dto.ProductDetailsDto;
import com.javaproject.eshop.entity.Product;
import com.javaproject.eshop.entity.ProductDetails;
import com.javaproject.eshop.exceptions.UnknownProductException;
import com.javaproject.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.doReturn;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private ProductDto productDto;
    private Product product;
    private ProductDetailsDto productDetailsDto;

    @BeforeEach
    void setup() {
        productDetailsDto = ProductDetailsDto.builder().color("blue").storageCapacity("128GB").build();
        productDto = new ProductDto("Samsung", 20, 20, productDetailsDto);
        product = Product.builder()
                .displayName(productDto.getDisplayName())
                .price(productDto.getPrice())
                .stock(productDto.getStock())
                .build();
    }

    @Test
    @DisplayName("Save product test")
    void saveProduct() {

        ProductDetails productDetails = ProductDetails.builder()
                .color(productDetailsDto.getColor())
                .storageCapacity(productDetailsDto.getStorageCapacity())
                .build();

        productDetails.setProduct(product);
        product.setDetails(productDetails);

        doReturn(product).when(productRepository).save(product);

        Product result = productService.saveProduct(productDto);

        assertEquals("Samsung", result.getDisplayName());
        assertEquals(20, result.getPrice());
        assertEquals(20, result.getStock());

    }

    @Test
    @DisplayName("Get product success test")
    void getProductSuccess() {
        int productId = 1;
        product.setProductId(1);
        doReturn(Optional.of(product)).when(productRepository).findProductByProductId(productId);

        Product result = productService.getProduct(productId);

        assertEquals(1, result.getProductId());
        assertEquals("Samsung", result.getDisplayName());
        assertEquals(20, result.getPrice());
        assertEquals(20, result.getStock());
    }

    @Test
    @DisplayName("Get product throw test")
    void getProductThrow() {
        String expected = "Product not found";

        UnknownProductException result = assertThrows(UnknownProductException.class,
                () -> productService.getProduct(1));

        assertEquals(expected, result.getMessage());
    }

//    @Test
//    @DisplayName("Get products test")
//    void getProducts() {
//        doReturn(List.of(product)).when(productRepository).findAll();
//
//        List<Product> result = productService.getProducts();
//
//        assertEquals(1, result.size());
//    }

    @Test
    @DisplayName("Update stock test")
    void updateStock() {
        product.setProductId(1);
        doReturn(Optional.of(product)).when(productRepository).findProductByProductId(1);

        assertEquals(20, product.getStock());

        productService.updateStock(1, 40);

        assertEquals(40, product.getStock());

    }
}
