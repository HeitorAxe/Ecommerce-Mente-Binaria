package com.compassuol.sp.challenge.ecommerce.entity;

import com.compassuol.sp.challenge.ecommerce.common.ProductConstants;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static com.compassuol.sp.challenge.ecommerce.common.ProductConstants.PRODUCT;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository productRepository;

    @Test
    void createProduct_WithValidData_ReturnProduct(){
        when(productRepository.save(ProductConstants.PRODUCT)).thenReturn(ProductConstants.PRODUCT);
        Product sut = productService.createProduct(ProductConstants.PRODUCT);
        assertThat(sut).isEqualTo(ProductConstants.PRODUCT);

    }
    @Test
    void createProduct_WithInvalidData_ThrowsException(){
        when(productRepository.save(ProductConstants.INVALID_PRODUCT)).thenThrow(RuntimeException.class);
        assertThatThrownBy(() -> productService.createProduct(ProductConstants.INVALID_PRODUCT)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void getAllProductsAsPage_ReturnsAllProducts(){
        List<ProductProjection> products = new ArrayList<>() {
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
        Page<ProductProjection> page = new PageImpl<>(products, PageRequest.of(0, 10), 1);
        when(productRepository.findAllAsProjection(any())).thenReturn(page);
        Page<ProductProjection> sut = productService.getAllAsPage(PageRequest.of(0, 10));
        assertThat(sut).isNotEmpty();
        assertThat(sut).hasSize(1);
        assertThat(sut.getContent().get(0).getName()).isEqualTo("Smartphone");
    }
    @Test
    void getAllProductsAsList_ReturnsAllProducts(){
        List<Product> products = new ArrayList<>() {
            {
                add(PRODUCT);
            }
        };
        when(productRepository.findAll()).thenReturn(products);
        List<Product> sut = productService.getAll();
        assertThat(sut).isNotEmpty();
        assertThat(sut).hasSize(1);
        assertThat(sut.get(0).getName()).isEqualTo(PRODUCT.getName());

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
    void updateProduct_WithInvalidId() {
        when(productRepository.findById(anyLong())).thenThrow(EntityNotFoundException.class);

        assertThatThrownBy(() -> productService.getById(1L)).isInstanceOf(EntityNotFoundException.class);

        verify(productRepository).findById(1L);
    }
    @Test
    void updateProduct_WithExistingName_ThrowsException(){
        when(productRepository.save(PRODUCT)).thenThrow(ProductNameUniqueViolationException.class);
        assertThatThrownBy(() -> productService.updateProduct(PRODUCT)).isInstanceOf(DataIntegrityViolationException.class);

    }
}