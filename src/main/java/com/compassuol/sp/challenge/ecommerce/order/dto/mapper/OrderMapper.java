package com.compassuol.sp.challenge.ecommerce.order.dto.mapper;

import com.compassuol.sp.challenge.ecommerce.order.dto.*;
import com.compassuol.sp.challenge.ecommerce.order.entity.Address;
import com.compassuol.sp.challenge.ecommerce.order.entity.Order;
import com.compassuol.sp.challenge.ecommerce.order.enums.PaymentMethod;
import com.compassuol.sp.challenge.ecommerce.product.service.ProductService;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.io.IOUtils.skip;

public class OrderMapper {
    ProductService productService;

    public static Order toOrder(OrderCreateDTO dto, Order order) {
        ModelMapper mapper = new ModelMapper();
        order.setAddress(mapper.map(dto.getAddress(), Address.class));
        order.setPaymentMethod(PaymentMethod.valueOf(dto.getPaymentMethod()));
        return order;
    }


    public static OrderResponseDTO toDTO(Order order) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        return mapper.map(order, OrderResponseDTO.class);
    }

    public static OrderResponseDeleteDTO toDtoDelete(Order order) {
        ModelMapper mapper = new ModelMapper();
        //contar produtos na lista
        return mapper.map(order, OrderResponseDeleteDTO.class);
    }


    public static List<OrderResponseDTO> toListDto(List<Order> orders) {
        return orders.stream().map(order-> toDTO(order)).collect(Collectors.toList());
    }

}
