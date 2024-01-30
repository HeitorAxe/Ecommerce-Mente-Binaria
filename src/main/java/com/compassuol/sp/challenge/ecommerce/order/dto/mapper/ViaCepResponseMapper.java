package com.compassuol.sp.challenge.ecommerce.order.dto.mapper;

import com.compassuol.sp.challenge.ecommerce.order.dto.ViaCepResponseDTO;
import com.compassuol.sp.challenge.ecommerce.order.entity.Address;


public class ViaCepResponseMapper {
    public static void complementAddress(ViaCepResponseDTO dto, Address address){
        if(address.getCity()==null)
            address.setCity(dto.getCity());
        if(address.getStreet()==null)
            address.setStreet(dto.getStreet());
        if(address.getState()==null)
            address.setState(dto.getState());
    }
}
