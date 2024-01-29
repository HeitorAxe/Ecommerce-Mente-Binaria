package com.compassuol.sp.challenge.ecommerce.order.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderResponseDTO {
    List<OrderHasProductDTO> products;
    private AddressDTO address;
    private String paymentMethod;
}
