package com.compassuol.sp.challenge.ecommerce.order.controller;

import com.compassuol.sp.challenge.ecommerce.order.dto.OrderCreateDTO;
import com.compassuol.sp.challenge.ecommerce.order.dto.OrderResponseDTO;
import com.compassuol.sp.challenge.ecommerce.order.dto.OrderUpdateDTO;
import com.compassuol.sp.challenge.ecommerce.order.dto.mapper.OrderMapper;
import com.compassuol.sp.challenge.ecommerce.order.entity.Order;
import com.compassuol.sp.challenge.ecommerce.order.service.OrderService;
import com.compassuol.sp.challenge.ecommerce.product.dto.PageableDTO;
import com.compassuol.sp.challenge.ecommerce.product.dto.ProductResponseDTO;
import com.compassuol.sp.challenge.ecommerce.product.service.ProductService;
import jakarta.persistence.PreRemove;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderCreateDTO createDto){
        OrderResponseDTO order = orderService.createOrder(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    public ResponseEntity<PageableDTO> getAllAsPage(@PageableDefault(size = 5) Pageable pageable){
        return null;

    }
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>>getAll(){
        List<Order> orders = orderService.getAll();
        return ResponseEntity.ok(OrderMapper.toListDto(orders));
    }
}


   /* public ResponseEntity<OrderResponseDTO> getById(@PathVariable Long id) {

    }*/

   /* @DeleteMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> deleteOrder(@PathVariable("id") Long id, @RequestBody OrderResponseDTO orderResponseDTO){
        orderService.removeOrder(id);
        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDTO);
    }*/


  /*  public ResponseEntity<ProductResponseDTO> updateOrderId(@PathVariable("id") Long id, @Valid @RequestBody OrderUpdateDTO orderDto) {
        return null;

    }*/


