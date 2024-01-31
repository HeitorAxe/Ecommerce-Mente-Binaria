package com.compassuol.sp.challenge.ecommerce.order.controller;

import com.compassuol.sp.challenge.ecommerce.order.dto.OrderDeleteDTO;
import com.compassuol.sp.challenge.ecommerce.order.exception.OrderStatusNotAuthorizedException;
import com.compassuol.sp.challenge.ecommerce.order.repository.OrderRepository;
import com.compassuol.sp.challenge.ecommerce.order.service.OrderService;
import com.compassuol.sp.challenge.ecommerce.product.controller.ProductController;
import com.compassuol.sp.challenge.ecommerce.product.repository.ProductRepository;
import com.compassuol.sp.challenge.ecommerce.product.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static com.compassuol.sp.challenge.ecommerce.common.OrderConstants.*;
import static com.compassuol.sp.challenge.ecommerce.common.ProductConstants.PRODUCT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    OrderService orderService;
    @MockBean
    OrderRepository orderRepository;

    @AfterEach
    public void afterEach(){
        ORDER_WITH_STATUS_CONFIRMED.setId(null);
        ORDER_WITH_STATUS_SENT.setId(null);

    }

    @Test
    void createOrder() {
    }

    @Test
    void getAllAsPage() {
    }

    @Test
    void getAll() {
    }

    @Test
    void getById() {
    }

    @Test
    void removeProduct_WithValidData_ReturnsOrderWithOrderStatusCanceled() throws Exception{
        OrderDeleteDTO dto = new OrderDeleteDTO("odiei o produto");
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(ORDER_WITH_STATUS_CONFIRMED));
        mockMvc.perform(delete("/orders/100")
                .content(objectMapper.writeValueAsString(dto))
                .contentType(
                        MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void removeProduct_WithInvalidData_Returns() throws Exception{
        OrderDeleteDTO dto = new OrderDeleteDTO("odiei o produto");
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(ORDER_WITH_STATUS_SENT));
        when(orderService.removeOrder(anyLong(), any())).thenThrow(OrderStatusNotAuthorizedException.class);
        mockMvc.perform(delete("/orders/101")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(
                                MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        mockMvc.perform(delete("/orders/a")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(
                                MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateOrderId() {
    }
}