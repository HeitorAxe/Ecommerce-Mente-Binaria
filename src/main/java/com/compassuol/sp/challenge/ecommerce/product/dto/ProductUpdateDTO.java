package com.compassuol.sp.challenge.ecommerce.product.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter @NoArgsConstructor @AllArgsConstructor
public class ProductUpdateDTO {
    @Size(min=1, message="Name must not be blank")
    private String name;
    @Size(min = 10, max = 100)
    private String description;
    @Positive(message = "Price must be a positive value")
    private Double price;
}
