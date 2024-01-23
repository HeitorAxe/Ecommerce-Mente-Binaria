package com.compassuol.sp.challenge.ecommerce.product.repository.projection;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface ProductProjection {
    String getId();
    String getName();
    String getDescription();
    String getPrice();
}
