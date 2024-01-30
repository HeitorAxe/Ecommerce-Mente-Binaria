package com.compassuol.sp.challenge.ecommerce.order.dto.mapper;

import com.compassuol.sp.challenge.ecommerce.order.dto.*;
import com.compassuol.sp.challenge.ecommerce.order.entity.Order;
import com.compassuol.sp.challenge.ecommerce.order.entity.OrderHasProduct;
import com.compassuol.sp.challenge.ecommerce.product.service.ProductService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.io.IOUtils.skip;

public class OrderMapper {
    ProductService productService;

    public static Order toOrder(OrderCreateDTO dto) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        mapper.addMappings(new PropertyMap<OrderHasProductDTO, OrderHasProduct>() {
            protected void configure() {skip(destination.getId());}
        });
        Order order = mapper.map(dto, Order.class);
        return order;
    }

    public static OrderResponseDTO toDTO(Order order) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        return mapper.map(order, OrderResponseDTO.class);
    }


    public static Order toCancelOrder(OrderDeleteDTO dto) {
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(new PropertyMap<OrderHasProductDTO, OrderHasProduct>() {
            protected void configure() {skip(destination.getId());}
        });
        return mapper.map(dto, Order.class);
    }
    public static OrderResponseDeleteDTO toDtoDelete(Order order) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(order, OrderResponseDeleteDTO.class);
    }


    public static List<OrderResponseDTO> toListDto(List<Order> orders) {
        return orders.stream().map(order-> toDTO(order)).collect(Collectors.toList());
    }

}
