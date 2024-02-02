package com.compassuol.sp.challenge.ecommerce.order.repository.projection;

import com.compassuol.sp.challenge.ecommerce.order.dto.AddressDTO;
import com.compassuol.sp.challenge.ecommerce.order.dto.OrderHasProductDTO;
import com.compassuol.sp.challenge.ecommerce.order.entity.Address;
import com.compassuol.sp.challenge.ecommerce.order.enums.OrderStatus;
import com.compassuol.sp.challenge.ecommerce.order.enums.PaymentMethod;
import com.compassuol.sp.challenge.ecommerce.product.entity.Product;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderProjection {
    Long getId();
    Address getAddress();
    PaymentMethod getPaymentMethod();
    OrderStatus getOrderStatus();
    boolean isHasDiscount();
    Double getTotalValue();
    Double getSubTotalValue();
    List<Product> getProducts();
    LocalDateTime getCreationDate();
    LocalDateTime getCancelationDate();
    String getCancelReason();

}
