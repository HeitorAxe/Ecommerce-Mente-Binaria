package com.compassuol.sp.challenge.ecommerce.order.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderHasProductDTO {
    @NotNull(message = "Must specify product id")
    private Long productId;
    @NotNull(message = "Must specify quantity")
    @Positive(message = "Price must be a positive value")
    private int quantity;
}
