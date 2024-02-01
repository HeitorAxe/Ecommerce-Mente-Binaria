package com.compassuol.sp.challenge.ecommerce.product.dto.mapper;

import com.compassuol.sp.challenge.ecommerce.product.dto.PageableDTO;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

public class PageableMapper {
    public static PageableDTO toDTO(Page page){

        return new ModelMapper().map(page, PageableDTO.class);
    }
}
