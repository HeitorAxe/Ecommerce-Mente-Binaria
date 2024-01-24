package com.compassouol.sp.challenge.ecommerce.domain;

import com.compassuol.sp.challenge.ecommerce.product.entity.Product;
import com.compassuol.sp.challenge.ecommerce.product.repository.ProductRepository;
import com.compassuol.sp.challenge.ecommerce.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.compassouol.sp.challenge.ecommerce.common.ProductConstants.INVALID_PRODUCT;
import static com.compassouol.sp.challenge.ecommerce.common.ProductConstants.PRODUCT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository planetRepository;

    @Test
    void createProduct_WithValidData_ReturnProduct(){
        when(planetRepository.save(PRODUCT)).thenReturn(PRODUCT);
        Product sut = productService.createProduct(PRODUCT);
        assertThat(sut).isEqualTo(PRODUCT);

    }
    @Test
    void createProduct_WithInvalidData_ThrowsException(){
        when(planetRepository.save(INVALID_PRODUCT)).thenThrow(RuntimeException.class);
        assertThatThrownBy(() -> productService.createProduct(INVALID_PRODUCT)).isInstanceOf(RuntimeException.class);
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
