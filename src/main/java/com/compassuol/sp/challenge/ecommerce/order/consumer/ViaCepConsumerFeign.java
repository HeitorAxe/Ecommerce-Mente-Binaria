package com.compassuol.sp.challenge.ecommerce.order.consumer;

import com.compassuol.sp.challenge.ecommerce.order.dto.ViaCepResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "viaCep-consumer", url = "https://viacep.com.br/ws/")
public interface ViaCepConsumerFeign {
    @GetMapping(value = "/{zip_code}/json/")
    ViaCepResponseDTO getAddressByPostalCode(@PathVariable("zip_code") String zipCode);
}
