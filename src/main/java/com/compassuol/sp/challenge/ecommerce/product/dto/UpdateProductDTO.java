package com.compassuol.sp.challenge.ecommerce.product.dto;

import lombok.*;

@Getter
@Setter @NoArgsConstructor @AllArgsConstructor
public class UpdateProductDTO {
    private String name;
    private String description;
    private Double price;
}
