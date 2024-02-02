package com.compassuol.sp.challenge.ecommerce.order.controller;

import com.compassuol.sp.challenge.ecommerce.handler.ErrorMessage;
import com.compassuol.sp.challenge.ecommerce.order.dto.OrderCreateDTO;
import com.compassuol.sp.challenge.ecommerce.order.dto.OrderDeleteDTO;
import com.compassuol.sp.challenge.ecommerce.order.dto.OrderResponseDTO;
import com.compassuol.sp.challenge.ecommerce.order.dto.OrderUpdateDTO;
import com.compassuol.sp.challenge.ecommerce.order.dto.mapper.OrderMapper;
import com.compassuol.sp.challenge.ecommerce.order.entity.Order;
import com.compassuol.sp.challenge.ecommerce.order.service.OrderService;
import com.compassuol.sp.challenge.ecommerce.product.dto.PageableDTO;
import com.compassuol.sp.challenge.ecommerce.product.dto.ProductResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name ="Orders", description = "Contains all operations to register, edit, delete, view an order.")
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Create order", description = "This operation allows clients to create an order. No authentication is required for this operation ",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Successful creation of order",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = OrderResponseDTO.class))),
                    @ApiResponse(responseCode = "422", description = "Unprocessable Entity - The request contains invalid parameters",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Product not found",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request - The request is poorly formatted",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),

            })
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderCreateDTO createDto){
        OrderResponseDTO order = orderService.createOrder(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
    @Operation(summary = "Get all products as pageable", description = "Retrieve products as pageable",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of products retrieved successfully as pageable",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = PageableDTO.class)))
            })
    @GetMapping("/page")
    public ResponseEntity<PageableDTO> getAllAsPage(@PageableDefault(size = 5) Pageable pageable){
       PageableDTO dto = orderService.getAllAsPage(pageable);
       return ResponseEntity.ok(dto);
    }

    @Operation(summary = "List all registered products", description = "No authentication required",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List all registered products",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    array = @ArraySchema(schema = @Schema(implementation = OrderResponseDTO.class))))
            })
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>>getAll(){
        List<OrderResponseDTO> orders = orderService.getAll();
        return ResponseEntity.ok(orders);
    }

    @Operation(summary = "Retrieve a order by id", description = "This operation allows clients to retrieve details of an order based on its unique identifier. No authentication is required for this operation ",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful retrieval of the order",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = OrderResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request - The request is poorly formatted or contains invalid parameters",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Order not found",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))

            })
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
    public ResponseEntity<OrderResponseDTO> deleteOrder(@PathVariable("id") Long id, @Valid @RequestBody OrderDeleteDTO deleteDto){
        OrderResponseDTO order = orderService.removeOrder(id, deleteDto);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    @Operation(summary = "Update order", description = "Customers can update the order",
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
