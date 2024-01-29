package com.compassuol.sp.challenge.ecommerce.entity;

import com.compassuol.sp.challenge.ecommerce.common.ProductConstants;
import com.compassuol.sp.challenge.ecommerce.product.dto.PageableDTO;
import com.compassuol.sp.challenge.ecommerce.product.dto.ProductResponseDTO;
import com.compassuol.sp.challenge.ecommerce.product.entity.Product;
import com.compassuol.sp.challenge.ecommerce.product.exception.ProductNameUniqueViolationException;
import com.compassuol.sp.challenge.ecommerce.product.repository.ProductRepository;
import com.compassuol.sp.challenge.ecommerce.product.repository.projection.ProductProjection;
import com.compassuol.sp.challenge.ecommerce.product.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.compassuol.sp.challenge.ecommerce.common.ProductConstants.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository productRepository;

    @Test
    void createProduct_WithValidData_ReturnProduct(){
        when(productRepository.save(ProductConstants.PRODUCT)).thenReturn(ProductConstants.PRODUCT);
        ProductResponseDTO sut = productService.createProduct(PRODUCT_CREATE_DTO);
        assertThat(sut.getName()).isEqualTo(PRODUCT_RESPONSE_DTO.getName());

    }
    @Test
    void createProduct_WithInvalidData_ThrowsException(){
        when(productRepository.save(ProductConstants.INVALID_PRODUCT)).thenThrow(RuntimeException.class);
        assertThatThrownBy(() -> productService.createProduct(INVALID_PRODUCT_CREATE_DTO)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void createProduct_ExistingName_ThrowsException(){
        when(productRepository.save(PRODUCT)).thenThrow(ProductNameUniqueViolationException.class);
        assertThatThrownBy(() -> productService.createProduct(PRODUCT_CREATE_DTO)).isInstanceOf(ProductNameUniqueViolationException.class);
    }

    @Test
    void getAllProductsAsPage_ReturnsAllProducts(){

        Page<ProductProjection> page = new PageImpl<>(PRODUCT_PROJECTIONS, PageRequest.of(0, 10), 1);
        when(productRepository.findAllAsProjection(any())).thenReturn(page);
        PageableDTO sut = productService.getAllAsPage(PAGEABLE);
        assertThat(sut.getContent()).isNotEmpty();
        assertThat(sut.getContent()).hasSize(1);
        assertThat(sut.getContent().get(0)).isEqualTo(PRODUCT_PROJECTIONS.get(0));
    }
    @Test
    void getAllProductsAsList_ReturnsAllProducts(){
        List<Product> products = new ArrayList<>() {
            {
                add(PRODUCT);
            }
        };
        when(productRepository.findAll()).thenReturn(products);
        List<ProductResponseDTO> sut = productService.getAll();
        assertThat(sut).isNotEmpty();
        assertThat(sut).hasSize(1);
        assertThat(sut.get(0).getName()).isEqualTo(PRODUCT.getName());

    }



    @Test
    void getAllProductsAsPage_ReturnsNoProducts(){
        Page<ProductProjection> page = new PageImpl<>(PRODUCT_PROJECTIONS_EMPTY, PageRequest.of(0, 10), 1);
        when(productRepository.findAllAsProjection(any())).thenReturn(page);
        PageableDTO sut = productService.getAllAsPage(PageRequest.of(0, 10));
        assertThat(sut.getContent()).isEmpty();
        assertThat(sut.getContent()).hasSize(0);
    }

    @Test
    void getAllProductsAsList_ReturnsNoProducts(){
        List<Product> products = new ArrayList<>();
        when(productRepository.findAll()).thenReturn(products);
        List<ProductResponseDTO> sut = productService.getAll();
        assertThat(sut).isEmpty();
        assertThat(sut).hasSize(0);
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
        ProductResponseDTO result = productService.getById(productId);
        assertThat(result).isNotNull()
                .extracting(ProductResponseDTO::getId, ProductResponseDTO::getName, ProductResponseDTO::getDescription, ProductResponseDTO::getPrice)
                .containsExactly(1L, "Tv", "Description bem", 1000.0);
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void removeProduct_WithExistingId_doesNotThrowAnyException() {
        ProductRepository productRepositoryMock = mock(ProductRepository.class);
        Product existingProduct = new Product(1L,"Notebook", "Notebook de boa qualidade!!!!",2000.0);
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
    void updateProduct_WithValidData_ReturnsNewProduct(){
        when(productRepository.findByName(any())).thenReturn(null);
        when(productRepository.findById(any())).thenReturn(Optional.of(PRODUCT_WITH_ID));
        ProductResponseDTO response = productService.updateProduct(PRODUCT_UPDATE_DTO, 1L);
        assertThat(response.getName()).isEqualTo(PRODUCT_RESPONSE_DTO.getName());
        assertThat(response.getId()).isEqualTo(PRODUCT_RESPONSE_DTO.getId());
        assertThat(response.getDescription()).isEqualTo(PRODUCT_RESPONSE_DTO.getDescription());
        assertThat(response.getPrice()).isEqualTo(PRODUCT_RESPONSE_DTO.getPrice());
    }

    @Test
    void updateProduct_WithInvalidId() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> productService.updateProduct(PRODUCT_UPDATE_DTO, 1L)).isInstanceOf(EntityNotFoundException.class);
    }
    @Test
    void updateProduct_WithExistingName_ThrowsException(){
        when(productRepository.findByName(any())).thenReturn(PRODUCT);
        assertThatThrownBy(() -> productService.updateProduct(PRODUCT_UPDATE_DTO, 1L)).isInstanceOf(ProductNameUniqueViolationException.class);
    }
}