package com.compassuol.sp.challenge.ecommerce.product.dto.mapper;

import com.compassuol.sp.challenge.ecommerce.product.dto.ProductResponseDTO;
import com.compassuol.sp.challenge.ecommerce.product.entity.Product;
import org.modelmapper.ModelMapper;


public class ProductMapper {
    public static ProductResponseDTO toDTO(Product product){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(product, ProductResponseDTO.class);
    }
}
