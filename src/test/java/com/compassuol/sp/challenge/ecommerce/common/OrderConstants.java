package com.compassuol.sp.challenge.ecommerce.common;

import com.compassuol.sp.challenge.ecommerce.order.dto.AddressDTO;
import com.compassuol.sp.challenge.ecommerce.order.dto.OrderCreateDTO;
import com.compassuol.sp.challenge.ecommerce.order.dto.OrderHasProductDTO;
import com.compassuol.sp.challenge.ecommerce.order.dto.OrderResponseDTO;
import com.compassuol.sp.challenge.ecommerce.order.dto.mapper.OrderMapper;
import com.compassuol.sp.challenge.ecommerce.order.entity.Address;
import com.compassuol.sp.challenge.ecommerce.order.entity.Order;
import com.compassuol.sp.challenge.ecommerce.order.enums.OrderStatus;
import com.compassuol.sp.challenge.ecommerce.product.entity.Product;
import org.aspectj.weaver.ast.Or;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.compassuol.sp.challenge.ecommerce.order.enums.OrderStatus.CONFIRMED;
import static com.compassuol.sp.challenge.ecommerce.order.enums.OrderStatus.SENT;
import static com.compassuol.sp.challenge.ecommerce.order.enums.OrderStatus.CANCELED;

import static com.compassuol.sp.challenge.ecommerce.order.enums.PaymentMethod.PIX;
import static com.compassuol.sp.challenge.ecommerce.order.enums.PaymentMethod.BANK_TRANSFER;
import static com.compassuol.sp.challenge.ecommerce.order.enums.PaymentMethod.CREDIT_CARD;
import static com.compassuol.sp.challenge.ecommerce.order.enums.PaymentMethod.CRYPTOCURRENCY;
import static com.compassuol.sp.challenge.ecommerce.order.enums.PaymentMethod.GIFT_CARD;
import static com.compassuol.sp.challenge.ecommerce.order.enums.PaymentMethod.OTHER;

public class OrderConstants {

    private static Order ORDER_CONFIRMED;
    private static Order ORDER_SENT;
    private static Order ORDER_CANCELED;
    private static Product product;

    private static Address address = new Address(1L, 16, "Em frente a praça", "68990-000", "Tartarugalzinho", "Perpétuo Socorro", "Amapá");

    static {
        ORDER_CONFIRMED = new Order();
        ORDER_CONFIRMED.setId(100L);
        ORDER_CONFIRMED.setAddress(address);
        ORDER_CONFIRMED.setPaymentMethod(PIX);
        ORDER_CONFIRMED.setOrderStatus(CONFIRMED);
        ORDER_CONFIRMED.setHasDiscount(true);
        ORDER_CONFIRMED.setTotalValue(950.00);
        ORDER_CONFIRMED.setSubTotalValue(1000.00);
        ORDER_CONFIRMED.setCreationDate(LocalDateTime.now());
        ORDER_CONFIRMED.setCancelationDate(null);
        ORDER_CONFIRMED.setCancelReason(null);

        product = new Product(100L,"TV", "Tv linda grande e moderna", 500.00);
        ORDER_CONFIRMED.addProduct(product, 4);
    }
    public static Order ORDER_WITH_STATUS_CONFIRMED = ORDER_CONFIRMED;

    static {
        ORDER_SENT = new Order();
        ORDER_SENT.setId(101L);
        ORDER_SENT.setAddress(address);
        ORDER_SENT.setPaymentMethod(PIX);
        ORDER_SENT.setOrderStatus(SENT);
        ORDER_SENT.setHasDiscount(true);
        ORDER_SENT.setTotalValue(950.00);
        ORDER_SENT.setSubTotalValue(1000.00);
        ORDER_SENT.setCreationDate(LocalDateTime.now());
        ORDER_SENT.setCancelationDate(null);
        ORDER_SENT.setCancelReason(null);

        product = new Product(100L,"TV", "Tv linda grande e moderna", 500.00);
        ORDER_SENT.addProduct(product, 4);
    }
    public static Order ORDER_WITH_STATUS_SENT = ORDER_SENT;

    static {
        ORDER_CANCELED = new Order();
        ORDER_CANCELED.setId(103L);
        ORDER_CANCELED.setAddress(address);
        ORDER_CANCELED.setPaymentMethod(PIX);
        ORDER_CANCELED.setOrderStatus(CANCELED);
        ORDER_CANCELED.setHasDiscount(true);
        ORDER_CANCELED.setTotalValue(950.00);
        ORDER_CANCELED.setSubTotalValue(1000.00);
        ORDER_CANCELED.setCreationDate(LocalDateTime.now());
        ORDER_CANCELED.setCancelationDate(LocalDateTime.now().plusDays(89));
        ORDER_CANCELED.setCancelReason("Não gostei do produto");

        product = new Product(100L,"TV", "Tv linda grande e moderna", 500.00);
        ORDER_CANCELED.addProduct(product, 4);
    }
    public static Order ORDER_WITH_STATUS_CANCELED = ORDER_CANCELED;


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
