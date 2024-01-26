package com.compassuol.sp.challenge.ecommerce;

import com.compassuol.sp.challenge.ecommerce.product.dto.ProductResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = { "/sql/import_products.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = { "/sql/remove_products.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ProductIT {
    @Autowired
    WebTestClient testClient;

    @Test
    public void Products_ReturnsAllProducts() {
        List<ProductResponseDTO> responseBody = testClient.get()
                .uri("/products")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ProductResponseDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.size()).isEqualTo(1);
    }

    @Test
    public void GetRemoveProducts_ReturnsNoProductsAfterRemoval() {
        testClient.delete()
                .uri("/products/1")
                .exchange()
                .expectStatus().isNoContent();

        // Verify that no products exist after removal
        List<ProductResponseDTO> responseBody = testClient.get()
                .uri("/products")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ProductResponseDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody).isEmpty();
    }

}
