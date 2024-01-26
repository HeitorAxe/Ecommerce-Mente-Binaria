package com.compassuol.sp.challenge.ecommerce.entity;

import com.compassuol.sp.challenge.ecommerce.product.entity.Product;
import com.compassuol.sp.challenge.ecommerce.product.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.jdbc.Sql;

import java.util.stream.Stream;

import static com.compassuol.sp.challenge.ecommerce.common.ProductConstants.PRODUCT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

        assertThat(sut).isNotNull();
        assertThat(sut.getName()).isEqualTo(PRODUCT.getName());
    }
    @Test
    public void createProduct_WithInvalidData_ReturnsProduct(){ // invalido quando vazio ou null
        Product emptyProduct = null;
        Product invalidProduct = new Product("", "", 0.0);
        assertThatThrownBy(() -> repository.save(emptyProduct)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> repository.save(invalidProduct)).isInstanceOf(RuntimeException.class);
    }

    @Sql(scripts = "/sql/import_products.sql")
    @Test
    public void removeProduct_WithExistingId_RemovesProductFromDatabase() {
        repository.deleteById(1L);
        assertThat(repository.findById(1L)).isEmpty();
    }

}
