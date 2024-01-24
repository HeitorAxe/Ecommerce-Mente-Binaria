package com.compassuol.sp.challenge.ecommerce.product.exception;

public class ProductNameUniqueViolationException extends RuntimeException{
    public ProductNameUniqueViolationException(String message){
        super(message);
    }
}
