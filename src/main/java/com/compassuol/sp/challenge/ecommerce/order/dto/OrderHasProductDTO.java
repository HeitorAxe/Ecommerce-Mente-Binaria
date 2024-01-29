package com.compassuol.sp.challenge.ecommerce.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderHasProductDTO {
    private Long productId;
    private int quantity;
}
