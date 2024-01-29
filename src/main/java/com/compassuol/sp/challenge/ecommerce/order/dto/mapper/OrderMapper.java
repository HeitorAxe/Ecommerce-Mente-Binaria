package com.compassuol.sp.challenge.ecommerce.order.dto.mapper;

import com.compassuol.sp.challenge.ecommerce.order.dto.OrderCreateDTO;
import com.compassuol.sp.challenge.ecommerce.order.dto.OrderResponseDTO;
import com.compassuol.sp.challenge.ecommerce.order.entity.Order;
import com.compassuol.sp.challenge.ecommerce.order.entity.OrderHasProduct;
import com.compassuol.sp.challenge.ecommerce.product.dto.ProductResponseDTO;
import com.compassuol.sp.challenge.ecommerce.product.dto.mapper.ProductMapper;
import com.compassuol.sp.challenge.ecommerce.product.service.ProductService;
import org.modelmapper.ModelMapper;

public class OrderMapper {
    ProductService productService;
    public static Order toOrder(OrderCreateDTO dto){
        ModelMapper mapper = new ModelMapper();
        Order order = mapper.map(dto, Order.class);
        return order;
    }

    public static OrderResponseDTO toDTO(Order order){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(order, OrderResponseDTO.class);
    }
}
