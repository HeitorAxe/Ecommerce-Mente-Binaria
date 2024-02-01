package com.compassuol.sp.challenge.ecommerce.order.repository.projection;

import com.compassuol.sp.challenge.ecommerce.order.dto.AddressDTO;
import com.compassuol.sp.challenge.ecommerce.order.dto.OrderHasProductDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderProjection {
    List<OrderHasProductDTO> getProducts();
    AddressDTO getAddress();
    String getPaymentMethod();
    String getOrderStatus();
    Double getTotalValue();
    Double getSubTotalValue();
    LocalDateTime getCreationDate();
    LocalDateTime getCancelationDate();
    String getCancelReason();
}
