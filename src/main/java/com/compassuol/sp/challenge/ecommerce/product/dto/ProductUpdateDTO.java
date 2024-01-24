package com.compassuol.sp.challenge.ecommerce.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter @NoArgsConstructor @AllArgsConstructor
public class ProductUpdateDTO {

    @NotBlank(message = "Name cannot be blank ")
    private String name;
    @Size(min = 10, max = 100)
    @NotBlank(message = "Description cannot be blank")
    private String description;
    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be a positive value")
    private Double price;
}
