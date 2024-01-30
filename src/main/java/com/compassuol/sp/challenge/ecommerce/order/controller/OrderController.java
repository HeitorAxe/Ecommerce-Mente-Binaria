package com.compassuol.sp.challenge.ecommerce.order.controller;

import com.compassuol.sp.challenge.ecommerce.order.dto.OrderCreateDTO;
import com.compassuol.sp.challenge.ecommerce.order.dto.OrderResponseDTO;
import com.compassuol.sp.challenge.ecommerce.order.dto.OrderUpdateDTO;
import com.compassuol.sp.challenge.ecommerce.order.entity.Order;
import com.compassuol.sp.challenge.ecommerce.order.service.OrderService;
import com.compassuol.sp.challenge.ecommerce.product.dto.PageableDTO;
import com.compassuol.sp.challenge.ecommerce.product.dto.ProductResponseDTO;
import com.compassuol.sp.challenge.ecommerce.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getById(@PathVariable Long id) {
        if(id < 0){
            return ResponseEntity.badRequest().build();
        }
        OrderResponseDTO order = orderService.getbyId(id);
        return ResponseEntity.ok(order);
    }

    public ResponseEntity<Void> deleteOrder(@PathVariable("id") Long id){
        return null;

    }


    public ResponseEntity<ProductResponseDTO> updateOrderId(@PathVariable("id") Long id, @Valid @RequestBody OrderUpdateDTO orderDto) {
        return null;

    }

}
