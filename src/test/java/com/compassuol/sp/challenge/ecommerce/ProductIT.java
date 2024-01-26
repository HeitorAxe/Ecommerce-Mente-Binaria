package com.compassuol.sp.challenge.ecommerce;

import com.compassuol.sp.challenge.ecommerce.product.dto.PageableDTO;
import com.compassuol.sp.challenge.ecommerce.product.dto.ProductResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
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
    public void GetProducts_ReturnsAllProducts() {
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
  
      public void GetProductsAsPage_ReturnsProductsPage() {
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


}
