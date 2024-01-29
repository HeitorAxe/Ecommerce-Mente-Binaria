package com.compassuol.sp.challenge.ecommerce.order.service;

import com.compassuol.sp.challenge.ecommerce.order.dto.OrderCreateDTO;
import com.compassuol.sp.challenge.ecommerce.order.dto.OrderResponseDTO;
import com.compassuol.sp.challenge.ecommerce.order.dto.mapper.OrderMapper;
import com.compassuol.sp.challenge.ecommerce.order.entity.Order;
import com.compassuol.sp.challenge.ecommerce.order.entity.OrderHasProduct;
import com.compassuol.sp.challenge.ecommerce.order.repository.AddressRepository;
import com.compassuol.sp.challenge.ecommerce.order.repository.OrderHasProductRepository;
import com.compassuol.sp.challenge.ecommerce.order.repository.OrderRepository;
import com.compassuol.sp.challenge.ecommerce.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final OrderHasProductRepository orderHasProductRepository;
    private final ProductService productService;

    @Transactional
    public OrderResponseDTO createOrder(OrderCreateDTO createDto) {
        Order order = OrderMapper.toOrder(createDto);
        List<OrderHasProduct> products = new ArrayList<>(order.getProducts());
        order.setProducts(new ArrayList<>());
        addressRepository.save(order.getAddress());
        orderRepository.save(order);
        return OrderMapper.toDTO(order);
    }
}
