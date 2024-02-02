package com.compassuol.sp.challenge.ecommerce.product.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter @NoArgsConstructor @AllArgsConstructor
public class ProductUpdateDTO {
    @Size(min=1, message="Name must not be blank")
    private String name;
    @Size(min = 10, max = 100, message = "Size must be between 10 and 100")
    private String description;
    @Positive(message = "Price must be a positive value")
    private Double price;
}
