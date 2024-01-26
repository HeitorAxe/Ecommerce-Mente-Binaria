package com.compassuol.sp.challenge.ecommerce.entity;

import com.compassuol.sp.challenge.ecommerce.product.entity.Product;
import com.compassuol.sp.challenge.ecommerce.product.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static com.compassuol.sp.challenge.ecommerce.common.ProductConstants.INVALID_PRODUCT;
import static com.compassuol.sp.challenge.ecommerce.common.ProductConstants.PRODUCT;

@DataJpaTest
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository repository;

    @Autowired
    private TestEntityManager testEntityManager;

    @AfterEach
    public void afterEach(){
        PRODUCT.setId(null);
    }

    @Test
    public void createProduct_WithValidData_ReturnsProduct(){
        Product product = repository.save(PRODUCT);

        Product sut = testEntityManager.find(Product.class, product.getId());

        Assertions.assertThat(sut).isNotNull();
        Assertions.assertThat(sut.getName()).isEqualTo(PRODUCT.getName());
    }
    @Test
    public void createProduct_WithInvalidData_ReturnsProduct(){ // invalido quando vazio ou null
        Product emptyProduct = new Product();
        Product invalidProduct = new Product("", "", 0.0);

        Assertions.assertThatThrownBy(() -> repository.save(emptyProduct)).isInstanceOf(RuntimeException.class);
        Assertions.assertThatThrownBy(() -> repository.save(invalidProduct)).isInstanceOf(RuntimeException.class);
    }
}
