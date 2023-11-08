package io.berndruecker.onboarding.customer.rest;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import io.camunda.zeebe.client.ZeebeClient;

@RestController
public class CustomerOnboardingRestController {

  @Autowired
  private ZeebeClient client;

  @Value("${recipient.demo.email}")
  private String recipientDemoEmail;

  @PutMapping("/customer")
  public ResponseEntity<CustomerOnboardingResponse> onboardCustomer(ServerWebExchange exchange) {
    onboardCustomer();
    return ResponseEntity.status(HttpStatus.ACCEPTED).build();
  }

  public void onboardCustomer() {
    HashMap<String, Object> variables = new HashMap<String, Object>();
    variables.put("automaticProcessing", true);
    variables.put("someInput", "yeah");
    variables.put("customerName", "Bernd Ruecker");
    variables.put("customerEmail", recipientDemoEmail);

    client.newCreateInstanceCommand() //
        .bpmnProcessId("customer-onboarding") //
        .latestVersion() //
        .variables(variables) //
        .send().join();
  }

  public static class CustomerOnboardingResponse {
  }
}
