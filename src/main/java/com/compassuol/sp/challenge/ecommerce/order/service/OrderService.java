package com.compassuol.sp.challenge.ecommerce.order.service;

import com.compassuol.sp.challenge.ecommerce.handler.ErrorMessage;
import com.compassuol.sp.challenge.ecommerce.order.consumer.ViaCepConsumerFeign;
import com.compassuol.sp.challenge.ecommerce.order.dto.*;
import com.compassuol.sp.challenge.ecommerce.order.dto.mapper.OrderMapper;
import com.compassuol.sp.challenge.ecommerce.order.dto.mapper.ViaCepResponseMapper;
import com.compassuol.sp.challenge.ecommerce.order.entity.Order;
import com.compassuol.sp.challenge.ecommerce.order.entity.OrderHasProduct;

import com.compassuol.sp.challenge.ecommerce.order.exception.OrderStatusNotAuthorizedException;
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
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Objects;

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
