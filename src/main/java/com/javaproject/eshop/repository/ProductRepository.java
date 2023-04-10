package com.javaproject.eshop.repository;

import com.javaproject.eshop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findProductByProductId(Integer productId);

    @Query("select p from Product p")
    Page<Product> findPaginatedProducts(Pageable pageable);
}
