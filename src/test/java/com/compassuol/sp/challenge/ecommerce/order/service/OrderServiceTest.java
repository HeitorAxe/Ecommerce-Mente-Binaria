package com.compassuol.sp.challenge.ecommerce.order.service;

import com.compassuol.sp.challenge.ecommerce.common.ProductConstants;
import com.compassuol.sp.challenge.ecommerce.order.dto.OrderCreateDTO;
import com.compassuol.sp.challenge.ecommerce.order.dto.OrderDeleteDTO;
import com.compassuol.sp.challenge.ecommerce.order.dto.OrderResponseDTO;
import com.compassuol.sp.challenge.ecommerce.order.dto.mapper.OrderMapper;
import com.compassuol.sp.challenge.ecommerce.order.entity.Order;
import com.compassuol.sp.challenge.ecommerce.order.enums.OrderStatus;
import com.compassuol.sp.challenge.ecommerce.order.exception.OrderStatusNotAuthorizedException;
import com.compassuol.sp.challenge.ecommerce.order.repository.OrderRepository;
import com.compassuol.sp.challenge.ecommerce.product.dto.ProductResponseDTO;
import com.compassuol.sp.challenge.ecommerce.product.exception.ProductNameUniqueViolationException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.ui.ModelMap;

import java.util.Optional;

import static com.compassuol.sp.challenge.ecommerce.common.OrderConstants.ORDER_WITH_STATUS_CONFIRMED;
import static com.compassuol.sp.challenge.ecommerce.common.OrderConstants.ORDER_WITH_STATUS_SENT;
import static com.compassuol.sp.challenge.ecommerce.common.ProductConstants.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    OrderService orderService;
    @Mock
    OrderRepository orderRepository;
    @Test
    void createOrder() {
    }

    @Test
    void getbyId() {
    }

    @Test
    void getAll() {
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
        when(orderRepository.findById(ORDER_WITH_STATUS_SENT.getId())).thenReturn(Optional.of(ORDER_WITH_STATUS_SENT));

        assertThatThrownBy(() -> orderService.removeOrder(ORDER_WITH_STATUS_CONFIRMED.getId(), delete)).isInstanceOf(OrderStatusNotAuthorizedException.class);
    }

    @Test
    void updateOrder() {
    }
}