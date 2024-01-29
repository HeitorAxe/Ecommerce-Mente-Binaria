package com.compassuol.sp.challenge.ecommerce;

import com.compassuol.sp.challenge.ecommerce.handler.ErrorMessage;
import com.compassuol.sp.challenge.ecommerce.product.dto.PageableDTO;
import com.compassuol.sp.challenge.ecommerce.product.dto.ProductCreateDTO;
import com.compassuol.sp.challenge.ecommerce.product.dto.ProductResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = { "/sql/import_products.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = { "/sql/remove_products.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ProductIT {
    @Autowired
    WebTestClient testClient;
    @Test
    void createProduct_WithValidData_ReturnCreatedProductWithStatus201() {
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
    void createProduct_WithInvalidData_ReturnErrorMessageStatus422(){
        ErrorMessage responseBody = testClient.post()
                .uri("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ProductCreateDTO("", "", 0.0))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    void listProducts_ReturnProductListWithStatus200() {
        List<ProductResponseDTO> responseBody = testClient.get()
                .uri("/products")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ProductResponseDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.get(0).getId()).isEqualTo(100);
        org.assertj.core.api.Assertions.assertThat(responseBody.size()).isEqualTo(6);
    }


    @Test
    void listProductsAsPage_ReturnProductListAsPageWithStatus200() {
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
    void getProduct_WithExistingIdReturn200(){
        testClient
                .get()
                .uri("/products/100")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("id").isEqualTo(100)
                .jsonPath("price").isEqualTo(200);

    }

    @Test
    void getProduct_WithExistingIdReturn404(){
        testClient
                .get()
                .uri("/products/10")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo(404);

    }


    @Test
    void removeProducts_ReturnsNoProductsAfterRemoval() {
        testClient.delete()
                .uri("/products/100")
                .exchange()
                .expectStatus().isNoContent();

        testClient.get()
                .uri("/products/100")
                .exchange()
                .expectStatus().isNotFound();

    }

    @Test
    void returnsMethodNotAllowed() {
        testClient.post()
                .uri("/products/1")
                .exchange()
                .expectStatus().isEqualTo(405);
    }

    @Test
    void getProduct_InvalidIdType_returnsMethodArgumentTypeMismatch() {
        testClient.get()
                .uri("/products/asdasd")
                .exchange()
                .expectStatus().isEqualTo(400);
    }
    @Test
    void returnsResourceNotFound() {
        testClient.get()
                .uri("/airplanes")
                .exchange()
                .expectStatus().isEqualTo(404);
    }

    @Test
    void returnsMethodArgumentNotAllowedMismatch() {
        testClient.put()
                .uri("/products")
                .exchange()
                .expectStatus().isEqualTo(405);
    }

    @Test
    void createProduct_WithoutJsonBody_ReturnsBadRequest() {
        testClient.post()
                .uri("/products")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();


    }

    @Test
    void getAllAsPage_WithInvalidQueryField_ReturnsBadRequest() {
        testClient.get()
                .uri("/products/page?page=0&size=1&sort=string")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

    }


}
