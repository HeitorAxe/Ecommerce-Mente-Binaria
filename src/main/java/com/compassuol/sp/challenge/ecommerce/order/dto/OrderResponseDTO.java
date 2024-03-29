package com.compassuol.sp.challenge.ecommerce.order.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponseDTO {
    private Long id;
    List<OrderHasProductDTO> products;
    private AddressDTO address;
    private String paymentMethod;
    private String orderStatus;
    private Double totalValue;
    private Double subTotalValue;
    private LocalDateTime creationDate;
    private LocalDateTime cancelationDate;
    private String cancelReason;
}
