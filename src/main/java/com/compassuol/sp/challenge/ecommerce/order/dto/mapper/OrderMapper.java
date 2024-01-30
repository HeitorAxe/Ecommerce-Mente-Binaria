package com.compassuol.sp.challenge.ecommerce.order.dto.mapper;

import com.compassuol.sp.challenge.ecommerce.order.dto.OrderCreateDTO;
import com.compassuol.sp.challenge.ecommerce.order.dto.OrderHasProductDTO;
import com.compassuol.sp.challenge.ecommerce.order.dto.OrderResponseDTO;
import com.compassuol.sp.challenge.ecommerce.order.entity.Order;
import com.compassuol.sp.challenge.ecommerce.order.entity.OrderHasProduct;
import com.compassuol.sp.challenge.ecommerce.product.service.ProductService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import static org.apache.commons.io.IOUtils.skip;

public class OrderMapper {
    ProductService productService;

    public static Order toOrder(OrderCreateDTO dto) {
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(new PropertyMap<OrderHasProductDTO, OrderHasProduct>() {
            protected void configure() {skip(destination.getId());}
        });
        Order order = mapper.map(dto, Order.class);
        return order;
    }

    public static OrderResponseDTO toDTO(Order order) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(order, OrderResponseDTO.class);
    }
}
