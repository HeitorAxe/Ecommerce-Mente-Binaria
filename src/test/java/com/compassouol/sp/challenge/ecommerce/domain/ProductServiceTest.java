package com.compassouol.sp.challenge.ecommerce.domain;

import com.compassuol.sp.challenge.ecommerce.product.repository.ProductRepository;
import com.compassuol.sp.challenge.ecommerce.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository planetRepository;

    @Test
    void createProduct_WithValidData_ReturnProduct(){
        // Pablo
    }
    @Test
    void createProduct_WithInvalidData_ThrowsException(){
        // Pablo
    }

    @Test
    void getAllProductsAsPage_ReturnsAllProducts(){
        //Heitor
    }

    @Test
    void getAllProductsAsList_ReturnsAllProducts(){
        //Heitor
    }

    @Test
    void getAllProductsAsPage_ReturnsNoProducts(){
        //Heitor
    }

    @Test
    void getAllProductsAsList_ReturnsNoProducts(){
        //Heitor
    }

    @Test
    void  getProductById_ByNonexistentId_ReturnsEmpty() {
        //Loude
    }

    @Test
    void  getProductById_ByExistentId_ReturnsProduct() {
        //Loude
    }

    @Test
    void removeProduct_WithExistingId_doesNotThrownAnyException(){
        //Jeffley
    }

    @Test
    void removeProduct_WithNonexistingId_ThrowsException(){
        //Jeffley
    }

    @Test
    void updateProduct_WithValidDatas(){
        //Luiz
    }
    @Test
    void updateProduct_WithInvalidId(){
        //Luiz
    }
    @Test
    void updateProduct_WithInvalidDatas(){
        //Luiz
    }
}
