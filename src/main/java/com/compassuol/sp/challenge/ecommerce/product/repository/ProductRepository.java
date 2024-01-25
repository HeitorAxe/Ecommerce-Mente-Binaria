package com.compassuol.sp.challenge.ecommerce.product.repository;

import com.compassuol.sp.challenge.ecommerce.product.repository.projection.ProductProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.compassuol.sp.challenge.ecommerce.product.entity.Product;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p.id as id, p.name as name, p.description as description, p.price as price FROM Product p")
    Page<ProductProjection> findAllAsProjection(Pageable pageable);
    Product findByName(String newName);
}
