package com.compassuol.sp.challenge.ecommerce.common;

import com.compassuol.sp.challenge.ecommerce.product.entity.Product;
import com.compassuol.sp.challenge.ecommerce.product.repository.projection.ProductProjection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class ProductConstants {

    public static final Product PRODUCT = new Product("TV",  "Tv linda grande e moderna", 2000.00);
    public static final Product INVALID_PRODUCT = new Product("", "", 0.0);
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
        public int getPageNumber() {
            return 0;
        }

        @Override
        public int getPageSize() {
            return 5;
        }

        @Override
        public long getOffset() {
            return 0;
        }

        @Override
        public Sort getSort() {
            return Sort.by(Sort.Order.asc("name"));
        }

        @Override
        public Pageable next() {
            return null;
        }

        @Override
        public Pageable previousOrFirst() {
            return null;
        }

        @Override
        public Pageable first() {
            return null;
        }

        @Override
        public Pageable withPage(int pageNumber) {
            return null;
        }

        @Override
        public boolean hasPrevious() {
            return false;
        }
    };

}

