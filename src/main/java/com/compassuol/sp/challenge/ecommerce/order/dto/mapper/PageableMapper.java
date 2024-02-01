
package com.compassuol.sp.challenge.ecommerce.order.dto.mapper;

import com.compassuol.sp.challenge.ecommerce.order.dto.OrderResponseDTO;
import com.compassuol.sp.challenge.ecommerce.order.dto.PageableDto;
import com.compassuol.sp.challenge.ecommerce.order.entity.Order;
import com.compassuol.sp.challenge.ecommerce.product.dto.PageableDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageableMapper {
    public static PageableDTO toDto(Page<Order> orderPage) {
        List<OrderResponseDTO> orderDTOList = orderPage.getContent().stream()
                .map(OrderMapper::toDTO)
                .collect(Collectors.toList());

        PageableDTO pageableDTO = new PageableDTO();
        pageableDTO.setContent(orderDTOList);
        pageableDTO.setFirst(orderPage.isFirst());
        pageableDTO.setLast(orderPage.isLast());
        pageableDTO.setNumber(orderPage.getNumber());
        pageableDTO.setSize(orderPage.getSize());
        pageableDTO.setNumberOfElements(orderPage.getNumberOfElements());
        pageableDTO.setTotalPages(orderPage.getTotalPages());
        pageableDTO.setTotalElements((int) orderPage.getTotalElements());

        return pageableDTO;
    }

}
