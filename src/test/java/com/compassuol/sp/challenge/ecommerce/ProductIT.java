package com.compassuol.sp.challenge.ecommerce;

import com.compassuol.sp.challenge.ecommerce.product.dto.PageableDTO;
import com.compassuol.sp.challenge.ecommerce.product.dto.ProductCreateDTO;
import com.compassuol.sp.challenge.ecommerce.product.dto.ProductResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = { "/sql/import_products.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = { "/sql/remove_products.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ProductIT {
    @Autowired
    WebTestClient testClient;
    @Sql(scripts = {"/sql/remove_products.sql"}, executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    public void createProduct_WithValidData_ReturnCreatedProductWithStatus201() {
        ProductResponseDTO responseBody = testClient.post()
                .uri("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ProductCreateDTO("Avião de combate", "Descrição do Produto", 1.00))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ProductResponseDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getName()).isEqualTo("Avião de combate");
        org.assertj.core.api.Assertions.assertThat(responseBody.getDescription()).isEqualTo("Descrição do Produto");
        org.assertj.core.api.Assertions.assertThat(responseBody.getPrice()).isEqualTo(1.00);
    }

    @Test
    public void listProducts_ReturnProductListWithStatus200() {
        List<ProductResponseDTO> responseBody = testClient.get()
                .uri("/products")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ProductResponseDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.get(0).getId()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.size()).isEqualTo(6);
    }


    @Test
    public void listProductsAsPage_ReturnProductListAsPageWithStatus200() {
        List<PageableDTO> responseBody = testClient.get()
                .uri("/products/page")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(PageableDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.get(0).getNumber()).isEqualTo(0);
        org.assertj.core.api.Assertions.assertThat(responseBody.get(0).getSize()).isEqualTo(5);
        org.assertj.core.api.Assertions.assertThat(responseBody.get(0).getTotalElements()).isEqualTo(6);


    }

    @Test
    public void GetRemoveProducts_ReturnsNoProductsAfterRemoval() {
        testClient.delete()
                .uri("/products/1")
                .exchange()
                .expectStatus().isNoContent();

        testClient.get()
                .uri("/products/1")
                .exchange()
                .expectStatus().isNotFound();

    }

    @Test
    public void ReturnsMethodNotAllowed() {
        testClient.post()
                .uri("/products/1")
                .exchange()
                .expectStatus().isEqualTo(405);
    }

    @Test
    public void ReturnsMethodArgumentTypeMismatch() {
        testClient.get()
                .uri("/products/asdasd")
                .exchange()
                .expectStatus().isEqualTo(400);
    }
    @Test
    public void ReturnsResourceNotFound() {
        testClient.get()
                .uri("/airplanes")
                .exchange()
                .expectStatus().isEqualTo(404);
    }

    @Test
    public void ReturnsMethodArgumentNotAllowedMismatch() {
        testClient.put()
                .uri("/products")
                .exchange()
                .expectStatus().isEqualTo(405);
    }



}
