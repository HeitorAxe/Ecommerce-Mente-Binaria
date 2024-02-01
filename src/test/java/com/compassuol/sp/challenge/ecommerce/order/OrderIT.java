package com.compassuol.sp.challenge.ecommerce.order;

import com.compassuol.sp.challenge.ecommerce.handler.ErrorMessage;
import com.compassuol.sp.challenge.ecommerce.order.dto.OrderDeleteDTO;
import com.compassuol.sp.challenge.ecommerce.order.dto.OrderResponseDTO;
import com.compassuol.sp.challenge.ecommerce.product.dto.ProductCreateDTO;
import com.compassuol.sp.challenge.ecommerce.product.dto.ProductResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static com.compassuol.sp.challenge.ecommerce.common.OrderConstants.*;
import static com.compassuol.sp.challenge.ecommerce.order.enums.OrderStatus.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = { "/sql/import_orders.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = { "/sql/remove_orders.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class OrderIT {

    @Autowired
    WebTestClient testClient;

    @Test
    void deleteOrder_WithValidData_ReturnCreatedProductWithOrderStatusCanceledAndStatus201() {
        OrderResponseDTO responseBody = testClient.method(HttpMethod.DELETE)
                .uri("/orders/100")
                .bodyValue(new OrderDeleteDTO("não gostei"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(OrderResponseDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getOrderStatus()).isEqualTo("CANCELED");
        org.assertj.core.api.Assertions.assertThat(responseBody.getCancelReason()).isEqualTo("não gostei");
        org.assertj.core.api.Assertions.assertThat(responseBody.getCancelationDate()).isNotNull();
    }
    @Test
    void deleteOrder_WithStatusOrderSent_ReturnErrorMessage400() {
        ErrorMessage responseBody = testClient.method(HttpMethod.DELETE)
                .uri("/orders/101")
                .bodyValue(new OrderDeleteDTO("não gostei"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);
    }
    @Test
    void deleteOrder_WithInvalidData_ReturnErrorMessageStatus422() {
        ErrorMessage responseBody = testClient.method(HttpMethod.DELETE)
                .uri("/orders/100")
                .bodyValue(new OrderDeleteDTO(""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        testClient.method(HttpMethod.DELETE)
                .uri("/orders/100")
                .bodyValue(new OrderDeleteDTO(null))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    void createOrder_WithValidData_ReturnStatusOkAndOrder(){
        OrderResponseDTO responseBody = testClient.method(HttpMethod.POST)
                .uri("/orders")
                .bodyValue(VALID_CREATE_ORDER_DTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(OrderResponseDTO.class)
                .returnResult().getResponseBody();
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getOrderStatus()).isEqualTo(CONFIRMED.toString());
        org.assertj.core.api.Assertions.assertThat(responseBody.getAddress().getPostalCode()).isEqualTo(VALID_CREATE_ORDER_DTO.getAddress().getPostalCode());
        org.assertj.core.api.Assertions.assertThat(responseBody.getProducts().get(0).getProductId())
                .isEqualTo(VALID_CREATE_ORDER_DTO.getProducts().get(0).getProductId());
    }

    @Test
    void createOrder_WithInvalidParameter_ReturnsErrorMessageAndStatus422(){
        testClient.method(HttpMethod.POST)
                .uri("/orders")
                .bodyValue(INVALID_CREATE_ORDER_DTO)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();
    }

    @Test
    void createOrder_WithNonexistenProduct_ReturnsErrorMessageAndStatusNotFound(){
        testClient.method(HttpMethod.POST)
                .uri("/orders")
                .bodyValue(CREATE_ORDER_DTO_NONEXISTEN_PRODUCT_ID)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();
    }

    @Test
    void createOrder_WithInvalidBody_ReturnsErrorMessageAndStatusBadRequest(){
        testClient.method(HttpMethod.POST)
                .uri("/orders")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();
    }



}
