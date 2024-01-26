package com.compassuol.sp.challenge.ecommerce.entity;

import com.compassuol.sp.challenge.ecommerce.product.entity.Product;
import com.compassuol.sp.challenge.ecommerce.product.repository.ProductRepository;
import com.compassuol.sp.challenge.ecommerce.product.repository.projection.ProductProjection;
import com.compassuol.sp.challenge.ecommerce.product.service.ProductService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static com.compassuol.sp.challenge.ecommerce.common.ProductConstants.*;

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
        Product nullProduct = null;
        Product invalidProduct = new Product("nome", "descrição do nome");

        Assertions.assertThatThrownBy(() -> repository.save(nullProduct)).isInstanceOf(RuntimeException.class);
        Assertions.assertThatThrownBy(() -> repository.save(invalidProduct)).isInstanceOf(RuntimeException.class);
    }

    @Sql(scripts = "/sql/import_products.sql")
    @Test
    public void listAllProductsAsPage_ReturnsAllProductsProjections(){
        Page<ProductProjection> response = repository.findAllAsProjection(PAGEABLE);
        Assertions.assertThat(response).isNotEmpty();
        Assertions.assertThat(response.getContent().get(0).getName()).isEqualTo("Televisão");
    }


    @Test
    public void listAllProductsAsPage_ReturnsNoProductsProjections(){
        Page<ProductProjection> response = repository.findAllAsProjection(PAGEABLE);
        Assertions.assertThat(response).isEmpty();
    }

    @Sql(scripts = "/sql/import_products.sql")
    @Test
    public void listAllProducts_ReturnsAllProducts(){
        List<Product> response = repository.findAll();
        Assertions.assertThat(response).isNotEmpty();
        Assertions.assertThat(response.get(0).getName()).isEqualTo("Televisão");
    }


    //@Sql(scripts = "/sql/import_products.sql")
    @Test
    public void listAllProducts_ReturnsNoProducts(){
        List<Product> response = repository.findAll();
        Assertions.assertThat(response).isEmpty();
    }
    @Test
    public void updateProduct_WithValidData_ReturnProductUpdated(){
        Product product = repository.save(PRODUCT);

        product.setName("Game");
        product.setPrice(3500.00);
        product.setDescription("Um game muito divertido!");

        Product productUpdated = repository.save(product);
        Optional<Product> productRecuperadoOptinonal = repository.findById(productUpdated.getId());
        Product productRecuperado = productRecuperadoOptinonal.orElseThrow();

        Assertions.assertThat(productRecuperado.getName()).isEqualTo("Game");
        Assertions.assertThat(productRecuperado.getPrice()).isEqualTo(3500.00);
        Assertions.assertThat(productRecuperado.getDescription()).isEqualTo("Um game muito divertido!");

    }

    @Test
    public void updateProduct_WithExistingName(){
        Product product = repository.save(PRODUCT);


        product.setName("Game");
        product.setPrice(3500.00);
        product.setDescription("Um game muito divertido!");

        Product newProduct = new Product();
        newProduct.setId(2L);
        newProduct.setName("Playstation");
        newProduct.setPrice(4000.00);
        newProduct.setDescription("Uma revolução");

        repository.save(newProduct);

        newProduct.setName("Game");

        Product productRepetido = repository.findByName("Game");

        Assertions.assertThat(productRepetido).isNotNull();

    }
}
