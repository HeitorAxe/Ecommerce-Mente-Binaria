package com.compassuol.sp.challenge.ecommerce.order.exception;

public class OrderStatusNotAuthorizedException extends RuntimeException {
    public OrderStatusNotAuthorizedException(String s) {
        super(s);
    }
}
