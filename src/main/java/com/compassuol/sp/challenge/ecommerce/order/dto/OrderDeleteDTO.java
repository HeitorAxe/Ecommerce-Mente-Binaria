package com.compassuol.sp.challenge.ecommerce.order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDeleteDTO {
    @NotBlank(message = "cancel reason cannot be empty")
    private String cancelReason;

}
