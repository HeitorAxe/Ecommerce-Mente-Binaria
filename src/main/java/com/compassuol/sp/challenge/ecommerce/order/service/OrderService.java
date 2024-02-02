package com.compassuol.sp.challenge.ecommerce.order.service;

import com.compassuol.sp.challenge.ecommerce.order.consumer.ViaCepConsumerFeign;
import com.compassuol.sp.challenge.ecommerce.order.dto.*;
import com.compassuol.sp.challenge.ecommerce.order.dto.mapper.OrderMapper;
import com.compassuol.sp.challenge.ecommerce.order.dto.mapper.ViaCepResponseMapper;
import com.compassuol.sp.challenge.ecommerce.order.entity.Order;
import com.compassuol.sp.challenge.ecommerce.order.exception.PostalCodeNotFoundException;
import com.compassuol.sp.challenge.ecommerce.order.repository.AddressRepository;
import com.compassuol.sp.challenge.ecommerce.order.repository.OrderRepository;
import com.compassuol.sp.challenge.ecommerce.product.entity.Product;
import com.compassuol.sp.challenge.ecommerce.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

        createDto.getProducts().forEach(orderProduct -> {
            Product product = productRepository.findById(orderProduct.getProductId()).orElseThrow(
                    () -> new EntityNotFoundException(String.format("Product with id %s not found", orderProduct.getProductId()))
            );
            order.addProduct(product, orderProduct.getQuantity());
        });

        ViaCepResponseDTO viaCepResponse = viaCepConsumerFeign.getAddressByPostalCode(order.getAddress().getPostalCode());
        if (viaCepResponse.getPostalCode()==null) throw new PostalCodeNotFoundException("The postal code provided is invalid");
        ViaCepResponseMapper.complementAddress(viaCepResponse, order.getAddress());

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
    public OrderResponseDTO removeOrder(Long id, OrderDeleteDTO deleteDto) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Order id %d not found", id)));
        order.cancel(deleteDto.getCancelReason());
        return OrderMapper.toDTO(order);
    }

    public OrderResponseDTO updateOrder(Long id, OrderUpdateDTO orderDto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));
        OrderMapper.updateOrder(order, orderDto, productRepository, addressRepository, viaCepConsumerFeign);
        Order updatedOrder = orderRepository.save(order);
        return OrderMapper.toDTO(updatedOrder);
    }


}
