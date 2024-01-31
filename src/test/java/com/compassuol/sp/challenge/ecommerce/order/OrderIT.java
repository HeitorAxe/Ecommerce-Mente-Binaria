package com.compassuol.sp.challenge.ecommerce.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = { "/sql/import_orders.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = { "/sql/remove_orders.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class OrderIT {

    @Autowired
    WebTestClient webTestClient;

}