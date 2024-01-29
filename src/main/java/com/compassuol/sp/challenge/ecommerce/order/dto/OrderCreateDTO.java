package com.compassuol.sp.challenge.ecommerce.order.dto;

import com.compassuol.sp.challenge.ecommerce.order.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateDTO {

    List<OrderHasProductDTO> products;
    private AddressDTO address;
    private String paymentMethod;

}
