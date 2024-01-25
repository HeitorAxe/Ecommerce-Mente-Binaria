package com.compassouol.sp.challenge.ecommerce.domain;

import com.compassuol.sp.challenge.ecommerce.product.entity.Product;
import com.compassuol.sp.challenge.ecommerce.product.repository.ProductRepository;
import com.compassuol.sp.challenge.ecommerce.product.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.compassouol.sp.challenge.ecommerce.common.ProductConstants.INVALID_PRODUCT;
import static com.compassouol.sp.challenge.ecommerce.common.ProductConstants.PRODUCT;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;


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
        Long nonexistentProductId = 99L;
        when(productRepository.findById(nonexistentProductId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> productService.getById(nonexistentProductId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Product id " + nonexistentProductId + " not found");
        verify(productRepository, times(1)).findById(nonexistentProductId);
    }

    @Test
    void  getProductById_ByExistentId_ReturnsProduct() {
        Long productId = 1L;
        Product existingProduct = new Product(1L, "Tv", "Description bem", 1000.0);
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        Product result = productService.getById(productId);
        assertThat(result).isNotNull()
                .extracting(Product::getId, Product::getName, Product::getDescription, Product::getPrice)
                .containsExactly(1L, "Tv", "Description bem", 1000.0);
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void removeProduct_WithExistingId_doesNotThrowAnyException() {
        //assertThatCode(() -> productService.remove(1L)).doesNotThrowAnyException();
        ProductRepository productRepositoryMock = mock(ProductRepository.class);
        Product existingProduct = new Product();
        existingProduct.setId(1L);
        existingProduct.setName("Notebook");
        existingProduct.setDescription("Notebook de boa qualidade!!!!");
        existingProduct.setPrice(2000.0);
        when(productRepositoryMock.findById(1L)).thenReturn(java.util.Optional.of(existingProduct));
        ProductService productService = new ProductService(productRepositoryMock);
        assertThatCode(() -> productService.remove(1L)).doesNotThrowAnyException();
        verify(productRepositoryMock, times(1)).deleteById(1L);

    }

    @Test
    void removeProduct_WithNonexistingId_ThrowsException(){

        assertThatThrownBy(() -> productService.remove(99L)).isInstanceOf(RuntimeException.class);
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
