package org.speedflyer.example.dapr;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.speedflyer.example.dapr.model.Order;

/**
 * Interface definition for event producer
 */
@RegisterRestClient(configKey="dapr-api")
@Path("/v1.0/publish")
public interface EventProducer {
  
  /**
   * Event Order Created
   * @param order
   */
  @POST
  @Path("/order/order-created")
  public Response orderCreated(Order order);

}
