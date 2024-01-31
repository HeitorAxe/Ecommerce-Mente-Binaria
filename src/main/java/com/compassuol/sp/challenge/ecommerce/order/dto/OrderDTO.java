package com.compassuol.sp.challenge.ecommerce.order.dto;

import com.compassuol.sp.challenge.ecommerce.order.enums.OrderStatus;
import com.compassuol.sp.challenge.ecommerce.order.enums.PaymentMethod;
import com.compassuol.sp.challenge.ecommerce.product.dto.ProductResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    private AddressDTO address;
    private PaymentMethod paymentMethod;
    private OrderStatus orderStatus;
    private boolean hasDiscount;
    private Double totalValue;
    private Double subTotalValue;
    private List<ProductResponseDTO> products;
    private LocalDateTime creationDate;
    private LocalDateTime cancelationDate;
    private String cancelReason;
}
