package com.compassuol.sp.challenge.ecommerce.product.dto.mapper;

import com.compassuol.sp.challenge.ecommerce.product.dto.ProductResponseDTO;
import com.compassuol.sp.challenge.ecommerce.product.entity.Product;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;


public class ProductMapper {
    public static ProductResponseDTO toDTO(Product product){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(product, ProductResponseDTO.class);
    }

    public static List<ProductResponseDTO> toListDTO(List<Product> users){
        return users.stream().map(product->toDTO(product)).collect(Collectors.toList());
    }
}
