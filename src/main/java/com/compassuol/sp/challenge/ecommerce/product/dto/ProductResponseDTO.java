package com.compassuol.sp.challenge.ecommerce.product.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
}
