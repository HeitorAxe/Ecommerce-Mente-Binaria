package com.compassouol.sp.challenge.ecommerce.domain;

import com.compassuol.sp.challenge.ecommerce.product.entity.Product;
import com.compassuol.sp.challenge.ecommerce.product.repository.ProductRepository;
import com.compassuol.sp.challenge.ecommerce.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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
    ProductRepository productRepository;

    @Test
    void createProduct_WithValidData_ReturnProduct(){
        when(productRepository.save(PRODUCT)).thenReturn(PRODUCT);
        Product sut = productService.createProduct(PRODUCT);
        assertThat(sut).isEqualTo(PRODUCT);

    }
    @Test
    void createProduct_WithInvalidData_ThrowsException(){
        when(productRepository.save(INVALID_PRODUCT)).thenThrow(RuntimeException.class);
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
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Product> sut =productService.get(1L);
        assertThat(sut).isEmpty();
    }

    @Test
    void  getProductById_ByExistentId_ReturnsProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(PRODUCT));
        Optional<Product> sut =productService.get(1L);
        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(PRODUCT);

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
