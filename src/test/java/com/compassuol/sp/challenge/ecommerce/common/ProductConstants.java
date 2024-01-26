package com.compassuol.sp.challenge.ecommerce.common;

import com.compassuol.sp.challenge.ecommerce.product.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductConstants {

    public static final Product PRODUCT = new Product("TV",  "Tv linda grande e moderna", 2000.00);
    public static final Product INVALID_PRODUCT = new Product("", "", 0.0);


}

