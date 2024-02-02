package com.compassuol.sp.challenge.ecommerce.order.repository;

import com.compassuol.sp.challenge.ecommerce.order.entity.Order;
import com.compassuol.sp.challenge.ecommerce.order.repository.projection.OrderProjection;
import com.compassuol.sp.challenge.ecommerce.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository  extends JpaRepository<Order, Long> {
@Query("SELECT o FROM Order o")
    Page<OrderProjection> findAllAsProjection(Pageable pageable);

}
