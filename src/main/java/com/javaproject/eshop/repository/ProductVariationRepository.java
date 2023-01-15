package com.javaproject.eshop.repository;

import com.javaproject.eshop.entity.Product;
import com.javaproject.eshop.entity.ProductVariation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductVariationRepository extends JpaRepository<ProductVariation, Integer> {
    List<ProductVariation> findProductVariationByProduct(Product product);

    @Query(value = "SELECT pv FROM ProductVariation pv WHERE pv.product.productId = ?1")
    List<ProductVariation> findProductVariations(int productId);
}
