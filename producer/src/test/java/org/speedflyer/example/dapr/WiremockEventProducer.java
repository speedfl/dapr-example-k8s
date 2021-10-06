package org.speedflyer.example.dapr;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import java.util.Collections;
import java.util.Map;

import com.github.tomakehurst.wiremock.WireMockServer;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

public class WiremockEventProducer implements QuarkusTestResourceLifecycleManager {  

  private WireMockServer wireMockServer;

  @Override
  public Map<String, String> start() {
    wireMockServer = new WireMockServer();
    wireMockServer.start(); 

    stubFor(post(urlEqualTo("/v1.0/publish/order/order-created"))   
        .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withStatus(200)));

    return Collections.singletonMap("dapr-api/mp-rest/url", wireMockServer.baseUrl()); 
  }

  @Override
  public void stop() {
    if (null != wireMockServer) {
      wireMockServer.stop();  
    }
  }
}