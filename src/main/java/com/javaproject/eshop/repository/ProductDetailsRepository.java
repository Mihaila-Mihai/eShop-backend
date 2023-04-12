package com.javaproject.eshop.repository;

import com.javaproject.eshop.entity.Product;
import com.javaproject.eshop.entity.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductDetailsRepository extends JpaRepository<ProductDetails, Integer> {
    List<ProductDetails> findProductVariationByProduct(Product product);

    @Query(value = "SELECT pv FROM ProductDetails pv WHERE pv.product.productId = ?1")
    List<ProductDetails> findProductVariations(int productId);
}
