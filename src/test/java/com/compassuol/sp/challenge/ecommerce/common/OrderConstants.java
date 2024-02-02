package com.compassuol.sp.challenge.ecommerce.common;


import com.compassuol.sp.challenge.ecommerce.order.dto.AddressDTO;
import com.compassuol.sp.challenge.ecommerce.order.dto.OrderCreateDTO;
import com.compassuol.sp.challenge.ecommerce.order.dto.OrderHasProductDTO;
import com.compassuol.sp.challenge.ecommerce.order.dto.OrderResponseDTO;
import com.compassuol.sp.challenge.ecommerce.order.dto.mapper.OrderMapper;
import com.compassuol.sp.challenge.ecommerce.order.entity.Address;
import com.compassuol.sp.challenge.ecommerce.order.entity.Order;
import com.compassuol.sp.challenge.ecommerce.product.entity.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.compassuol.sp.challenge.ecommerce.order.enums.OrderStatus.*;
import static com.compassuol.sp.challenge.ecommerce.order.enums.PaymentMethod.PIX;

public class OrderConstants {

    private static Order ORDER_CONFIRMED;
    private static Order ORDER_SENT;
    private static Order ORDER_CANCELED;
    private static Product product;
    public static final Order ORDER = new Order();

    private static Address address = new Address(1L, 16, "Em frente a praça", "68990-000", "Tartarugalzinho", "Perpétuo Socorro", "Amapá");

    public static Order ORDER_WITH_STATUS_CONFIRMED = new Order(100L, address, PIX, CONFIRMED, true, 950.00, 1000.00, new ArrayList<>(), LocalDateTime.now(), null, null);
    static {
        Product product = new Product(100L,"TV", "Tv linda grande e moderna", 500.00);
        ORDER_WITH_STATUS_CONFIRMED.addProduct(product, 4);
    }

    public static Order ORDER_WITH_STATUS_SENT= new Order(101L, address, PIX, SENT, true, 950.00, 1000.00, new ArrayList<>(), LocalDateTime.now(), null, null);
    static {

        Product product = new Product(100L,"TV", "Tv linda grande e moderna", 500.00);
        ORDER_WITH_STATUS_SENT.addProduct(product, 4);
    }
    public static Order ORDER_WITH_STATUS_CANCELED = new Order(103L, address, PIX, CANCELED, true, 950.00, 1000.00, new ArrayList<>(), LocalDateTime.now(), LocalDateTime.now().plusDays(89), "Não gostei dos produtos");
    static {
        Product product = new Product(100L,"TV", "Tv linda grande e moderna", 500.00);
        ORDER_WITH_STATUS_CANCELED.addProduct(product, 4);
    }

    public static OrderHasProductDTO VALID_ORDER_HAS_PRODUCT_DTO = new OrderHasProductDTO(100L, 1);
    public static List<OrderHasProductDTO> VALID_LIST_ORDER_HAS_PRODUCT_DTO = new ArrayList<>();
    static{VALID_LIST_ORDER_HAS_PRODUCT_DTO.add(VALID_ORDER_HAS_PRODUCT_DTO);}
    public static AddressDTO VALID_ADDRESS_DTO = new AddressDTO(16, "Em frente a praça", "68990-000", "Tartarugalzinho", "Perpétuo Socorro", "Amapá");
    public static OrderCreateDTO VALID_CREATE_ORDER_DTO = new OrderCreateDTO(VALID_LIST_ORDER_HAS_PRODUCT_DTO, VALID_ADDRESS_DTO, "PIX");

    public static OrderHasProductDTO INVALID_ORDER_HAS_PRODUCT_DTO = new OrderHasProductDTO(354L, 1);
    public static List<OrderHasProductDTO> INVALID_LIST_ORDER_HAS_PRODUCT_DTO = new ArrayList<>();
    static{INVALID_LIST_ORDER_HAS_PRODUCT_DTO.add(INVALID_ORDER_HAS_PRODUCT_DTO);}
    public static OrderCreateDTO INVALID_CREATE_ORDER_DTO = new OrderCreateDTO(VALID_LIST_ORDER_HAS_PRODUCT_DTO, VALID_ADDRESS_DTO, "PAY_WITH_SOUL");
    public static OrderCreateDTO CREATE_ORDER_DTO_NONEXISTEN_PRODUCT_ID = new OrderCreateDTO(INVALID_LIST_ORDER_HAS_PRODUCT_DTO, VALID_ADDRESS_DTO, "PIX");
    public static OrderResponseDTO ORDER_RESPONSE_DTO = OrderMapper.toDTO(ORDER_WITH_STATUS_CONFIRMED);

}
