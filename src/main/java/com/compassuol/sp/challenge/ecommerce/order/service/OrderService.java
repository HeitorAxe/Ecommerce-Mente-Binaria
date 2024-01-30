package com.compassuol.sp.challenge.ecommerce.order.service;

import com.compassuol.sp.challenge.ecommerce.order.consumer.ViaCepConsumerFeign;
import com.compassuol.sp.challenge.ecommerce.order.dto.*;
import com.compassuol.sp.challenge.ecommerce.order.dto.mapper.OrderMapper;
import com.compassuol.sp.challenge.ecommerce.order.dto.mapper.ViaCepResponseMapper;
import com.compassuol.sp.challenge.ecommerce.order.entity.Order;
import com.compassuol.sp.challenge.ecommerce.order.entity.OrderHasProduct;
import com.compassuol.sp.challenge.ecommerce.order.repository.AddressRepository;
import com.compassuol.sp.challenge.ecommerce.order.repository.OrderHasProductRepository;
import com.compassuol.sp.challenge.ecommerce.order.repository.OrderRepository;
import com.compassuol.sp.challenge.ecommerce.product.dto.mapper.ProductMapper;
import com.compassuol.sp.challenge.ecommerce.product.repository.ProductRepository;
import com.compassuol.sp.challenge.ecommerce.product.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.compassuol.sp.challenge.ecommerce.order.enums.OrderStatus.CANCELED;
import static com.compassuol.sp.challenge.ecommerce.order.enums.OrderStatus.SENT;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;
    private final ViaCepConsumerFeign viaCepConsumerFeign;
    @Transactional
    public OrderResponseDTO createOrder(OrderCreateDTO createDto) {
        Order order = OrderMapper.toOrder(createDto);
        order.setCreationDate(LocalDateTime.now());
        ViaCepResponseDTO viaCepResponse = viaCepConsumerFeign.getAddressByPostalCode(order.getAddress().getPostalCode());
        ViaCepResponseMapper.complementAddress(viaCepResponse, order.getAddress());
        List<OrderHasProduct> products = new ArrayList<>(order.getProducts());
        order.setProducts(new ArrayList<>());
        addressRepository.save(order.getAddress());
        orderRepository.save(order);
        for(OrderHasProduct orderHasProduct: products){
            orderHasProduct.setOrder(order);
            orderHasProduct.setProduct(productRepository.findById(orderHasProduct.getProduct().getId()).orElseThrow(
                    () -> new RuntimeException("Produto não encontrado")
            ));
            order.getProducts().add(orderHasProduct);
        }
        order.updateValues();
        orderRepository.save(order);
        return OrderMapper.toDTO(order);
    }

    @Transactional
    public OrderResponseDeleteDTO removeOrder(Long id, OrderDeleteDTO deleteDto) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("Order id %d not found", id))
        );
        LocalDateTime purchasePeriod = order.getCreationDate().plusDays(90);
        if (order.getOrderStatus() == SENT && order.getCreationDate().isAfter(purchasePeriod)) {
            order.setOrderStatus(CANCELED);
            order.setCancelationDate(LocalDateTime.now());
            order.setCancelReason(deleteDto.getCancelReason());
            return OrderMapper.toDtoDelete(order);
        }else return null; // possivelmente retornará uma exceção

    }
}
