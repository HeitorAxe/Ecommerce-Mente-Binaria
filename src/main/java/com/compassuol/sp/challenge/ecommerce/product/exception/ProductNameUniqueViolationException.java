package com.compassuol.sp.challenge.ecommerce.product.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class ProductNameUniqueViolationException extends DataIntegrityViolationException {
    public ProductNameUniqueViolationException(String message){
        super(message);
    }
}
