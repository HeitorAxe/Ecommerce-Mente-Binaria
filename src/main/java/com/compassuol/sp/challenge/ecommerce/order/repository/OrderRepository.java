package com.compassuol.sp.challenge.ecommerce.order.repository;

import com.compassuol.sp.challenge.ecommerce.order.entity.Order;
import com.compassuol.sp.challenge.ecommerce.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository  extends JpaRepository<Order, Long> {

}
