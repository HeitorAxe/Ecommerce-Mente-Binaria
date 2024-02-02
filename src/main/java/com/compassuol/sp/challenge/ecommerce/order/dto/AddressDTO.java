package com.compassuol.sp.challenge.ecommerce.order.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDTO {
    @NotNull(message = "Must specify address number")
    private Integer number;
    @NotBlank(message = "Must specify address complement")
    private String complement;
    @NotBlank(message = "Must specify address postal code")
    private String postalCode;

    private String city;
    private String street;
    private String state;

}
