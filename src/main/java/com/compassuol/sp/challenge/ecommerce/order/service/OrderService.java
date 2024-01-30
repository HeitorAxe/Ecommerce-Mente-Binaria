package com.compassuol.sp.challenge.ecommerce.order.service;

import com.compassuol.sp.challenge.ecommerce.order.consumer.ViaCepConsumerFeign;
import com.compassuol.sp.challenge.ecommerce.order.dto.OrderCreateDTO;
import com.compassuol.sp.challenge.ecommerce.order.dto.OrderResponseDTO;
import com.compassuol.sp.challenge.ecommerce.order.dto.ViaCepResponseDTO;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        addressRepository.save(order.getAddress());
        orderRepository.save(order);
        for(OrderHasProduct orderHasProduct: order.getProducts()){
            orderHasProduct.setOrder(order);
            orderHasProduct.setProduct(productRepository.findById(orderHasProduct.getProduct().getId()).orElseThrow());
        }
        return OrderMapper.toDTO(order);
    }

    public void removeOrder(Long id, OrderResponseDTO dto) {
        if (dto.getPaymentMethod() == SENT && dto.get) {

        }
    }
}
