package org.speedflyer.example.dapr.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {

  private String id;
  
  private Long creationTime;
  
  private Amount total; 
}
