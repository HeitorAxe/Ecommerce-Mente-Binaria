package com.compassuol.sp.challenge.ecommerce.order.controller;

import com.compassuol.sp.challenge.ecommerce.handler.ErrorMessage;
import com.compassuol.sp.challenge.ecommerce.order.dto.*;
import com.compassuol.sp.challenge.ecommerce.order.dto.OrderCreateDTO;
import com.compassuol.sp.challenge.ecommerce.order.dto.OrderResponseDTO;
import com.compassuol.sp.challenge.ecommerce.order.dto.OrderUpdateDTO;
import com.compassuol.sp.challenge.ecommerce.order.dto.mapper.OrderMapper;
import com.compassuol.sp.challenge.ecommerce.order.entity.Order;
import com.compassuol.sp.challenge.ecommerce.order.service.OrderService;
import com.compassuol.sp.challenge.ecommerce.product.dto.PageableDTO;
import com.compassuol.sp.challenge.ecommerce.product.dto.ProductResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getById(@PathVariable Long id) {
        if(id < 0){
            return ResponseEntity.badRequest().build();
        }
        OrderResponseDTO order = orderService.getbyId(id);
        return ResponseEntity.ok(order);

    }
    @Operation(summary = "Cancel order with cancel reason", description = "Clients can cancel the order",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource retrieved successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ProductResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Poorly formatted resource or no reason message",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Resource not found",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> deleteOrder(@PathVariable("id") Long id, @RequestBody OrderDeleteDTO deleteDto){
        OrderResponseDTO order = orderService.removeOrder(id, deleteDto);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    @Operation(summary = "update order", description = "Customers can update the order",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource retrieved successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ProductResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request - malformed request syntax",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Resource not found",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PutMapping("/{id}")
   public ResponseEntity<OrderResponseDTO> updateOrderId(@PathVariable("id") Long id, @Valid @RequestBody OrderUpdateDTO orderDto) {
       OrderResponseDTO updatedOrder = orderService.updateOrder(id, orderDto);
       return ResponseEntity.ok(updatedOrder);

    }
}
