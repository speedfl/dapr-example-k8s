package org.speedflyer.example.dapr;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;
import org.speedflyer.example.dapr.model.Amount;
import org.speedflyer.example.dapr.model.Order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@QuarkusTestResource(WiremockEventProducer.class)
public class TestOrderResource {
  
  @Test
  public void testCreateOrder() throws JsonProcessingException {
    
    Order order = new Order();
    Amount total = new Amount();
    total.setAmount(1000);
    total.setCurrency("EUR");
    order.setTotal(total);
    
    ObjectMapper objectMapper = new ObjectMapper();
    
    given()
        .when()
        .contentType(ContentType.JSON)
        .body(objectMapper.writeValueAsString(order))
        .post("/order")
        .then()
        .statusCode(200);
  }

}