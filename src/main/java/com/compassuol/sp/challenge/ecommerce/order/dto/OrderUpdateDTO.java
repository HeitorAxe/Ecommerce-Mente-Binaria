package com.compassuol.sp.challenge.ecommerce.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateDTO {
    @Valid
    List<OrderHasProductDTO> products;
    @Valid
    private AddressDTO address;
    @Pattern(regexp = "CREDIT_CARD|" +
            "BANK_TRANSFER|" +
            "CRYPTOCURRENCY|" +
            "GIFT_CARD|" +
            "PIX|" +
            "OTHER"
            , message = "Must be a valid payment method: CREDIT_CARD|BANK_TRANSFER|CRYPTOCURRENCY|GIFT_CARD|PIX|OTHER")
    private String paymentMethod;
    @Pattern(regexp = "CONFIRMED|" +
            "SENT|" +
            "CANCELED"
            ,message = "Must be a valid order status: CONFIRMED|SENT|CANCELED")
    private String orderStatus;
}
