package com.compassuol.sp.challenge.ecommerce.common;

import com.compassuol.sp.challenge.ecommerce.product.dto.ProductCreateDTO;
import com.compassuol.sp.challenge.ecommerce.product.dto.ProductResponseDTO;
import com.compassuol.sp.challenge.ecommerce.product.dto.ProductUpdateDTO;
import com.compassuol.sp.challenge.ecommerce.product.entity.Product;
import com.compassuol.sp.challenge.ecommerce.product.repository.projection.ProductProjection;
import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class ProductConstants {

    public static final Product PRODUCT = new Product("TV",  "Tv linda grande e moderna", 2000.00);
    public static final Product PRODUCT_WITH_ID = new Product(1L, "TV",  "Tv linda grande e moderna", 2000.00);
    public static final Product INVALID_PRODUCT = new Product("", "", 0.0);
    public static final ProductResponseDTO PRODUCT_RESPONSE_DTO = new ProductResponseDTO(1L, "TV",  "Tv linda grande e moderna", 2000.00);
    public static final ProductCreateDTO PRODUCT_CREATE_DTO = new ProductCreateDTO("TV",  "Tv linda grande e moderna", 2000.00);
    public static final ProductCreateDTO INVALID_PRODUCT_CREATE_DTO = new ProductCreateDTO("",  "", 0.0);
    public static final ProductUpdateDTO PRODUCT_UPDATE_DTO = new ProductUpdateDTO("TV",  "Tv linda grande e moderna", 2000.00);

    public static final List<ProductProjection> PRODUCT_PROJECTIONS = new ArrayList<>() {
        {
            add(new ProductProjection() {
                @Override
                public String getId() {
                    return "1";
                }
                @Override
                public String getName() {
                    return "Smartphone";
                }
                @Override
                public String getDescription() {
                    return "A phone that is also smart";
                }
                @Override
                public String getPrice() {
                    return "1000.0";
                }
            });
        }
    };

    public static final List<ProductProjection> PRODUCT_PROJECTIONS_EMPTY = new ArrayList<>();
    public static final Pageable PAGEABLE = new Pageable() {
        @Override
        public int getPageNumber() {return 0;}
        @Override
        public int getPageSize() {return 5;}
        @Override
        public long getOffset() {return 0;}
        @NonNull
        @Override
        public Sort getSort() {return Sort.by(Sort.Order.asc("name"));}
        @NonNull
        @Override
        public Pageable next() {return PAGEABLE;}
        @Override
        @NonNull
        public Pageable previousOrFirst() {return PAGEABLE;}
        @Override
        @NonNull
        public Pageable first() {return PAGEABLE;}
        @Override
        @NonNull
        public Pageable withPage(int pageNumber) {return PAGEABLE;}
        @Override
        public boolean hasPrevious() {return false;}
    };

}

