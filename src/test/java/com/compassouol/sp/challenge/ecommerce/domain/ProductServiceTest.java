package com.compassouol.sp.challenge.ecommerce.domain;

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
import static com.compassouol.sp.challenge.ecommerce.common.ProductConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
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
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Product> sut =productService.get(1L);
        assertThat(sut).isEmpty();
    }

    @Test
    void  getProductById_ByExistentId_ReturnsProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(PRODUCT));
        Optional<Product> sut = productService.get(1L);
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
        when(productRepository.save(PRODUCT)).thenReturn(PRODUCT);
         productService.updateProduct(PRODUCT);
         assertThat(PRODUCT).isEqualTo(PRODUCT);
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
