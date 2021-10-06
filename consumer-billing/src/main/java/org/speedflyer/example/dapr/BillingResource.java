package org.speedflyer.example.dapr;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.speedflyer.example.dapr.model.CloudEventOrder;
import org.speedflyer.example.dapr.model.Order;

import lombok.extern.slf4j.Slf4j;

@Path("/order-created")
@Slf4j
public class BillingResource {

  @POST
  @Consumes({ "application/cloudevents+json", MediaType.APPLICATION_JSON })
  public Response billingOrder(CloudEventOrder event) {
    Order order = event.getData();
    log.info("{} # Storing billing information for order {}", event.getId(), order.getId());
    return Response.ok().build();
  }
}