package io.berndruecker.onboarding.customer.fakes;

import io.berndruecker.onboarding.customer.rest.CustomerOnboardingRestController;
import io.camunda.zeebe.client.ZeebeClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

@RestController
public class CrmServiceRestController {

    private Logger logger = LoggerFactory.getLogger(CustomerOnboardingRestController.class);

    @PutMapping("/crm/customer")
    public ResponseEntity<String> addCustomerToCrmFake(ServerWebExchange exchange) {
        logger.info("CRM REST API called");
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
