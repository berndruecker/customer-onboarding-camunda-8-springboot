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
    CustomerOnboardingResponse response =onboardCustomer();
    // return ResponseEntity.status(HttpStatus.OK).body(response);
    return ResponseEntity.status(HttpStatus.ACCEPTED).build();
  }

  public CustomerOnboardingResponse onboardCustomer() {
    CustomerOnboardingResponse response = new CustomerOnboardingResponse();

//    String scoringRequestId = UUID.randomUUID().toString();

    HashMap<String, Object> variables = new HashMap<String, Object>();
//    variables.put(ProcessConstants.VAR_SCORING_REQUEST_ID, scoringRequestId);
//    variables.put(ProcessConstants.VAR_SCORING_RETRY_COUNT, 0l);
    variables.put("automaticProcessing", true);
    variables.put("someInput", "yeah");

    // Start new instance of the ticket-booking workflow
    WorkflowInstanceEvent future = client.newCreateInstanceCommand() //
        .bpmnProcessId("customer-onboarding") //
        .latestVersion() //
        .variables(variables) //
        .send().join();

    return response;
  }

  public static class CustomerOnboardingResponse {
  }
}
