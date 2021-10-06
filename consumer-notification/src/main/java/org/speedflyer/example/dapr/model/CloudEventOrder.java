package org.speedflyer.example.dapr.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CloudEventOrder extends CloudEventV1Envelope<Order> {

  private Order data;
  
}
