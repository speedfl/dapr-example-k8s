package org.speedflyer.example.dapr.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class CloudEventV1Envelope<T> {

  private String specversion;
  
  private String type;
  
  private String source;
  
  private String subject;
  
  private String id;
  
  private String time;
  
  private String datacontenttype;
  
  public abstract T getData();
  
  public abstract void setData(T data);
  
}