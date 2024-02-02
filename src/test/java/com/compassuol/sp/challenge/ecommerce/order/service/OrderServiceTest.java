package com.compassuol.sp.challenge.ecommerce.order.service;

import com.compassuol.sp.challenge.ecommerce.common.OrderConstants;
import com.compassuol.sp.challenge.ecommerce.common.ProductConstants;
import com.compassuol.sp.challenge.ecommerce.order.dto.*;
import com.compassuol.sp.challenge.ecommerce.order.dto.mapper.OrderMapper;
import com.compassuol.sp.challenge.ecommerce.order.entity.Order;
import com.compassuol.sp.challenge.ecommerce.order.enums.OrderStatus;
import com.compassuol.sp.challenge.ecommerce.order.exception.OrderStatusNotAuthorizedException;
import com.compassuol.sp.challenge.ecommerce.order.repository.OrderRepository;
import com.compassuol.sp.challenge.ecommerce.order.repository.projection.OrderProjection;
import com.compassuol.sp.challenge.ecommerce.product.dto.PageableDTO;
import com.compassuol.sp.challenge.ecommerce.product.dto.ProductResponseDTO;
import com.compassuol.sp.challenge.ecommerce.product.entity.Product;
import com.compassuol.sp.challenge.ecommerce.product.exception.ProductNameUniqueViolationException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.compassuol.sp.challenge.ecommerce.common.OrderConstants.*;
import static com.compassuol.sp.challenge.ecommerce.common.ProductConstants.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.RequestEntity.put;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    OrderService orderService;
    @Mock
    OrderRepository orderRepository;
    @AfterEach
    public void afterEach(){
        ORDER_WITH_STATUS_CONFIRMED.setId(null);
        ORDER_WITH_STATUS_SENT.setId(null);
        //ORDER_WITH_ID.setId(null);
    }
    @Test
    void createOrder() {
    }


    @Test
    void removeProduct_WithValidData_ReturnsOrderWithOrderStatusCanceled() {
        OrderDeleteDTO delete = new OrderDeleteDTO("Não gostei do produto");
        when(orderRepository.findById(ORDER_WITH_STATUS_CONFIRMED.getId())).thenReturn(Optional.of(ORDER_WITH_STATUS_CONFIRMED));

        OrderResponseDTO sut = orderService.removeOrder(ORDER_WITH_STATUS_CONFIRMED.getId(), delete);
        assertThat(sut.getOrderStatus()).isEqualTo(String.valueOf(OrderStatus.CANCELED));
        assertThat(sut.getCancelationDate()).isNotNull();
        assertThat(sut.getCancelReason()).isEqualTo("Não gostei do produto");
    }

    @Test
    void removeOrder_WithInvalidOrderStatus_DoesThrowOrderStatusNotAuthorized() {
        OrderDeleteDTO delete = new OrderDeleteDTO("Não gostei do produto");
        when(orderRepository.findById(any())).thenReturn(Optional.of(ORDER_WITH_STATUS_SENT));

        assertThatThrownBy(() -> orderService.removeOrder(ORDER_WITH_STATUS_CONFIRMED.getId(), delete)).isInstanceOf(OrderStatusNotAuthorizedException.class);
    }

    @Test
    void  updateOrder_WithValidData_ReturnsNewOrder(){
        OrderUpdateDTO updateDto = new OrderUpdateDTO();
        updateDto.setPaymentMethod("CREDIT_CARD");
        updateDto.setOrderStatus(OrderStatus.SENT.toString());
        Order existingOrder = new Order();
        when(orderRepository.findById(100L)).thenReturn(Optional.of(existingOrder));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));
        OrderResponseDTO updatedOrder = orderService.updateOrder(100L, updateDto);
        assertNotNull(updatedOrder);
        assertEquals(updateDto.getPaymentMethod(), updatedOrder.getPaymentMethod());
        assertEquals(updateDto.getOrderStatus(), updatedOrder.getOrderStatus());
        assertEquals(updateDto.getAddress(), updatedOrder.getAddress());
    }
    @Test
    void updateOrder_WithInvalidId(){

        OrderUpdateDTO updateDto = new OrderUpdateDTO();
        when(orderRepository.findById(999L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> orderService.updateOrder(999L, updateDto))
                .isInstanceOf(EntityNotFoundException.class);
    }



    @Test
    void getAllORDERSAsList_ReturnsAllORDERS(){
        List<Order> orders = new ArrayList<>() {
            {
                add(ORDER);
            }
        };
        when(orderRepository.findAll()).thenReturn(orders);
        List<OrderResponseDTO> sut = orderService.getAll();
        assertThat(sut).isNotEmpty();
        assertThat(sut).hasSize(1);

    }


}

