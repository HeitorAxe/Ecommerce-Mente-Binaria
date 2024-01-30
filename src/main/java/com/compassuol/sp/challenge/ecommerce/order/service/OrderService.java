package com.compassuol.sp.challenge.ecommerce.order.service;

import com.compassuol.sp.challenge.ecommerce.order.consumer.ViaCepConsumerFeign;
import com.compassuol.sp.challenge.ecommerce.order.dto.*;
import com.compassuol.sp.challenge.ecommerce.order.dto.mapper.OrderMapper;
import com.compassuol.sp.challenge.ecommerce.order.dto.mapper.ViaCepResponseMapper;
import com.compassuol.sp.challenge.ecommerce.order.entity.Order;

import com.compassuol.sp.challenge.ecommerce.order.enums.OrderStatus;
import com.compassuol.sp.challenge.ecommerce.order.enums.PaymentMethod;
import com.compassuol.sp.challenge.ecommerce.order.exception.OrderStatusNotAuthorizedException;
import com.compassuol.sp.challenge.ecommerce.order.repository.AddressRepository;
import com.compassuol.sp.challenge.ecommerce.order.repository.OrderRepository;
import com.compassuol.sp.challenge.ecommerce.product.entity.Product;
import com.compassuol.sp.challenge.ecommerce.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static com.compassuol.sp.challenge.ecommerce.order.enums.OrderStatus.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;
    private final ViaCepConsumerFeign viaCepConsumerFeign;
    @Transactional
    public OrderResponseDTO createOrder(OrderCreateDTO createDto) {
        Order order = new Order();
        OrderMapper.toOrder(createDto, order);
        for(OrderHasProductDTO orderProduct : createDto.getProducts())
            for(int i = 0; i<orderProduct.getQuantity(); i++)
                order.getProducts().add(productRepository.findById(orderProduct.getProductId()).orElseThrow());
        ViaCepResponseDTO viaCepResponse = viaCepConsumerFeign.getAddressByPostalCode(order.getAddress().getPostalCode());
        ViaCepResponseMapper.complementAddress(viaCepResponse, order.getAddress());
        order.setCreationDate(LocalDateTime.now());
        addressRepository.save(order.getAddress());
        orderRepository.save(order);
        return OrderMapper.toDTO(order);
    }

    @Transactional
    public OrderResponseDTO getbyId(Long id) {
    Order order = orderRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException(String.format("Order %d not found", id))
    );
    return OrderMapper.toDTO(order);
    }

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Transactional
    public OrderResponseDeleteDTO removeOrder(Long id, OrderDeleteDTO deleteDto) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Order id %d not found", id))
        );
        LocalDateTime purchasePeriod = order.getCreationDate().plusDays(90);
        if (order.getOrderStatus() == CONFIRMED && LocalDateTime.now().isBefore(purchasePeriod)){ // Se agora for antes da data limite do cancelamento
                order.setOrderStatus(CANCELED);
                order.setCancelationDate(LocalDateTime.now());
                order.setCancelReason(deleteDto.getCancelReason());
        } else if (order.getOrderStatus() != CONFIRMED) {
            throw new OrderStatusNotAuthorizedException(
                    "O status do pedido deve ser diferente de SENT"
            );
        }else if (LocalDateTime.now().isAfter(purchasePeriod)){
            throw new OrderStatusNotAuthorizedException(
                    "O cancelamento do pedido só pode ser feito antes de 90 dias da compra"
            );
        }
        return OrderMapper.toDtoDelete(order);

    }

    public OrderResponseDTO updateOrder(Long id, OrderUpdateDTO orderDto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));
        if (orderDto.getPaymentMethod() != null) {
            order.setPaymentMethod(PaymentMethod.valueOf(orderDto.getPaymentMethod()));
        }
        if (orderDto.getOrderStatus() != null) {
            order.setOrderStatus(OrderStatus.valueOf(orderDto.getOrderStatus()));
        }
        Order updatedOrder = orderRepository.save(order);
        return OrderMapper.toDTO(updatedOrder);
    }
}
