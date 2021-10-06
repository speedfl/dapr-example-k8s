package org.speedflyer.example.dapr;

import static io.restassured.RestAssured.given;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.speedflyer.example.dapr.model.Amount;
import org.speedflyer.example.dapr.model.CloudEventOrder;
import org.speedflyer.example.dapr.model.Order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class TestBillingResource {

  @Test
  public void billingOrder() throws JsonProcessingException {
    
    CloudEventOrder cloudEventV1 = new CloudEventOrder();
    cloudEventV1.setId(UUID.randomUUID().toString());
    cloudEventV1.setSpecversion("1.0");
    cloudEventV1.setSource("https://example.com/message");
    cloudEventV1.setSubject("Test JSON Message");
    cloudEventV1.setTime("2020-09-23T06:23:21Z");
    cloudEventV1.setType("json.message");
    cloudEventV1.setDatacontenttype("application/json");
    Order order = new Order();
    order.setId(UUID.randomUUID().toString());
    Amount total = new Amount();
    total.setAmount(1000);
    total.setCurrency("EUR");
    order.setTotal(total);
    
    cloudEventV1.setData(order);
    
    ObjectMapper objectMapper = new ObjectMapper();
    
    given()
        .when()
        .contentType(ContentType.JSON)
        .body(objectMapper.writeValueAsString(cloudEventV1))
        .post("/order-created")
        .then()
        .statusCode(200);
  }

}