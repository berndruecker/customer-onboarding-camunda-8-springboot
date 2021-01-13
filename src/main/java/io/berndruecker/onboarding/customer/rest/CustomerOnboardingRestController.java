package io.berndruecker.onboarding.customer.rest;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.response.WorkflowInstanceEvent;
import io.zeebe.spring.client.EnableZeebeClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import java.util.HashMap;

@RestController
public class CustomerOnboardingRestController {

  private Logger logger = LoggerFactory.getLogger(CustomerOnboardingRestController.class);

  @Autowired
  private ZeebeClient client;

  @PutMapping("/customer")
  public ResponseEntity<CustomerOnboardingResponse> onboardCustomer(ServerWebExchange exchange) {
    // TODO: Build variant with synchronous facade
    //CustomerOnboardingResponse response = onboardCustomer();
    // return ResponseEntity.status(HttpStatus.OK).body(response);

    onboardCustomer();
    return ResponseEntity.status(HttpStatus.ACCEPTED).build();
  }

//  public CustomerOnboardingResponse onboardCustomer() {
//    CustomerOnboardingResponse response = new CustomerOnboardingResponse();
//    String scoringRequestId = UUID.randomUUID().toString();

  public void onboardCustomer() {
    HashMap<String, Object> variables = new HashMap<String, Object>();
    variables.put("automaticProcessing", true);
    variables.put("someInput", "yeah");

    //    WorkflowInstanceEvent future = client.newCreateInstanceCommand() //
    client.newCreateInstanceCommand() //
        .bpmnProcessId("customer-onboarding") //
        .latestVersion() //
        .variables(variables) //
        .send().join();
  }

  public static class CustomerOnboardingResponse {
  }
}
