package com.javaproject.eshop.service;

import com.javaproject.eshop.dto.ProductDto;
import com.javaproject.eshop.entity.Product;
import com.javaproject.eshop.entity.ProductVariation;
import com.javaproject.eshop.exceptions.UnknownProductException;
import com.javaproject.eshop.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Product saveProduct(ProductDto productDto) {
        Product product = Product.builder()
                .displayName(productDto.getDisplayName())
                .price(productDto.getPrice())
                .stock(productDto.getStock())
                .build();

        List<ProductVariation> productVariations = productDto.getVariations().stream()
                .map(productVariation -> ProductVariation.builder()
                        .product(product)
                        .color(productVariation.getColor())
                        .storageCapacity(productVariation.getStorageCapacity())
                        .build())
                .toList();

        product.setVariations(productVariations);

        Product savedProduct = productRepository.save(product);

        return productRepository.save(savedProduct);
    }

    public Product getProduct(int productId) {
        return productRepository.findProductByProductId(productId).orElseThrow(() -> new UnknownProductException("Product not found"));
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }
    public Page<Product> getPaginatedProducts(int page, int size, List<String> sortList, String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(createSortOrder(sortList, sortOrder)));
        return productRepository.findPaginatedProducts(pageable);
    }

    private List<Sort.Order> createSortOrder(List<String> sortList, String sortDirection) {
        List<Sort.Order> sorts = new ArrayList<>();
        Sort.Direction direction;
        for (String sort : sortList) {
            if (sortDirection != null) {
                direction = Sort.Direction.fromString(sortDirection);
            } else {
                direction = Sort.Direction.DESC;
            }
            sorts.add(new Sort.Order(direction, sort));
        }
        return sorts;
    }

    public void updateStock(int productId, int stock) {
        Product product = productRepository.findProductByProductId(productId)
                .orElseThrow(() -> new UnknownProductException("Product not found"));
        if (product != null) {
            productRepository.save(product.setStock(stock));
        }
    }
}
