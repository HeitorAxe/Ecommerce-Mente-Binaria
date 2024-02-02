package com.compassuol.sp.challenge.ecommerce.order.service;

import com.compassuol.sp.challenge.ecommerce.order.dto.OrderDeleteDTO;
import com.compassuol.sp.challenge.ecommerce.order.dto.OrderResponseDTO;
import com.compassuol.sp.challenge.ecommerce.order.dto.OrderUpdateDTO;
import com.compassuol.sp.challenge.ecommerce.order.entity.Order;
import com.compassuol.sp.challenge.ecommerce.order.enums.OrderStatus;
import com.compassuol.sp.challenge.ecommerce.order.exception.OrderStatusNotAuthorizedException;
import com.compassuol.sp.challenge.ecommerce.order.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.compassuol.sp.challenge.ecommerce.common.OrderConstants.ORDER_WITH_STATUS_CONFIRMED;
import static com.compassuol.sp.challenge.ecommerce.common.OrderConstants.ORDER_WITH_STATUS_SENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

}