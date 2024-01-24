package com.compassuol.sp.challenge.ecommerce.product;

import com.compassuol.sp.challenge.ecommerce.product.repository.ProductRepository;
import com.compassuol.sp.challenge.ecommerce.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UnitTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void removeProduct_WithExistingId_doesNotThrowAnyException204(){
        Long productId = 1L;
        doNothing().when(productRepository).deleteById(productId);
        productService.remove(productId);
        verify(productRepository).deleteById(productId);
    }
    @Test
    public void removeProduct_WithNotExistingId_doesNotThrowAnyException(){
        doThrow(new RuntimeException()).when(productRepository).deleteById(99L);

        assertThatThrownBy(() -> productService.remove(99L)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void removeProduct_WithNotExistingId_doesNotThrowAnyException404(){
        Long nonExistingProductId = 99L;
        doThrow(new ProductNotFoundException("Product not found")).when(productRepository).deleteById(nonExistingProductId);

        assertThatThrownBy(() -> productService.remove(nonExistingProductId))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessage("Product not found");

        verify(productRepository).deleteById(nonExistingProductId);
    }

}
