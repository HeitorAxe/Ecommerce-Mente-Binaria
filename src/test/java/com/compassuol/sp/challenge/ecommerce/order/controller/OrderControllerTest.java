package com.compassuol.sp.challenge.ecommerce.order.controller;
import com.compassuol.sp.challenge.ecommerce.order.dto.OrderDeleteDTO;
import com.compassuol.sp.challenge.ecommerce.order.dto.OrderResponseDTO;
import com.compassuol.sp.challenge.ecommerce.common.OrderConstants;
import com.compassuol.sp.challenge.ecommerce.order.consumer.ViaCepConsumerFeign;
import com.compassuol.sp.challenge.ecommerce.order.dto.*;
import com.compassuol.sp.challenge.ecommerce.order.enums.OrderStatus;
import com.compassuol.sp.challenge.ecommerce.order.exception.OrderStatusNotAuthorizedException;
import com.compassuol.sp.challenge.ecommerce.order.repository.AddressRepository;
import com.compassuol.sp.challenge.ecommerce.order.repository.OrderRepository;
import com.compassuol.sp.challenge.ecommerce.order.service.OrderService;
import com.compassuol.sp.challenge.ecommerce.product.controller.ProductController;
import com.compassuol.sp.challenge.ecommerce.product.repository.ProductRepository;
import com.compassuol.sp.challenge.ecommerce.product.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.Optional;
import static com.compassuol.sp.challenge.ecommerce.common.OrderConstants.*;

import static com.compassuol.sp.challenge.ecommerce.common.ProductConstants.PRODUCT;
import static org.hamcrest.Matchers.hasSize;

import static com.compassuol.sp.challenge.ecommerce.common.ProductConstants.PRODUCT_RESPONSE_DTO;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    @Mock
    AddressRepository addressRepository;

    @Mock
    ViaCepConsumerFeign viaCepConsumerFeign;

    @AfterEach
    public void afterEach(){
        ORDER_WITH_STATUS_CONFIRMED.setId(null);
        ORDER_WITH_STATUS_SENT.setId(null);
    }

    @Test
    void createOrder_WithValidData_ReturnsStatusIsCreated() throws Exception {
        //OrderDeleteDTO dto = new OrderDeleteDTO("odiei o produto");
        when(orderService.createOrder(any())).thenReturn(ORDER_RESPONSE_DTO);
        mockMvc.perform(post("/orders")
                        .content(objectMapper.writeValueAsString(VALID_CREATE_ORDER_DTO))
                        .contentType(
                                MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void createOrder_WithNonexistentProductId_ThrowsExceptionNotFound() throws Exception {
        when(orderService.createOrder(any())).thenThrow(new EntityNotFoundException());
        mockMvc.perform(post("/orders")
                        .content(objectMapper.writeValueAsString(VALID_CREATE_ORDER_DTO))
                        .contentType(
                                MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllAsPage() {
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
   void updateOrder_WithValidData_ReturnsNewOrder() throws Exception {
        when(orderRepository.findById(1L)).thenReturn(Optional.ofNullable(ORDER_WITH_STATUS_CONFIRMED));

        mockMvc.perform(
                        get("/orders/1")
                )
                .andExpect(status().isOk());
    }

    @Test
    void updateOrder_WithInvalidId() throws Exception {

        OrderUpdateDTO updateDto = new OrderUpdateDTO();
        when(orderService.updateOrder(999L, updateDto))
                .thenThrow(EntityNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.put("/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}