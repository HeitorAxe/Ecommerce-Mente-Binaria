package com.compassuol.sp.challenge.ecommerce.common;

import com.compassuol.sp.challenge.ecommerce.product.repository.ProductRepository;
import com.compassuol.sp.challenge.ecommerce.product.service.ProductService;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.repository.CrudRepository;

import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class UnitTest {
    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository productRepository;

    @Test
    void removeProduct_WithExistingId_doesNotThrowAnyException() {
        // Configurar o comportamento esperado, se necessário

        // Verificar se a chamada ao método deleteById não lança exceções
        assertThatCode(() -> productService.remove(1L)).doesNotThrowAnyException();

        // Verificar se o método deleteById foi chamado com o ID correto
        verify(productRepository).deleteById(1L);
    }

    @Test
    public void removePlanet_WithUnexistingId_ThrowsException() {
        lenient().doThrow(new RuntimeException()).when(productRepository).deleteById(99L);

        assertThatThrownBy(() -> productService.remove(99L)).isInstanceOf(RuntimeException.class);
    }
}
