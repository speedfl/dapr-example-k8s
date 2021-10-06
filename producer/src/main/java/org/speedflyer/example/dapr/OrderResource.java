package org.speedflyer.example.dapr;

import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.speedflyer.example.dapr.model.Order;

import lombok.extern.slf4j.Slf4j;

@Path("/order")
@Slf4j
public class OrderResource {

  @Inject
  @RestClient
  EventProducer eventProducer;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Order createOrder(Order order) {
    log.info("Received order creation");
    order.setId(UUID.randomUUID().toString());
    order.setCreationTime(System.currentTimeMillis());
    log.info("Order {} created at {}", order.getId(), order.getCreationTime());
    eventProducer.orderCreated(order);
    log.info("Notification sent");
    return order;
  }
}