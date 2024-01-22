package com.compassuol.sp.challenge.ecommerce.product.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.compassuol.sp.challenge.ecommerce.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
