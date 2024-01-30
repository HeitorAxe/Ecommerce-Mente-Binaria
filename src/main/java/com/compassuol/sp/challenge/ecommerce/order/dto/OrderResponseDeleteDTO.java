package com.compassuol.sp.challenge.ecommerce.order.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderResponseDeleteDTO {
    List<OrderHasProductDTO> products;
    private AddressDTO address;
    private String paymentMethod;

    private Double totalValue;
    private Double subTotalValue;
    private LocalDateTime creationDate;
    private LocalDateTime cancelationDate;
    private String cancelReason;

}
