package io.berndruecker.onboarding.customer.process;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CustomerOnboardingGlueCode {

    private static Logger logger = LoggerFactory.getLogger(CustomerOnboardingGlueCode.class);

    // This would be of course injected and depends on the environment. Hard coded for now
    public static String ENDPOINT_CRM = "http://localhost:8080/crm/customer";

    @Autowired
    private RestTemplate restTemplate;

    @JobWorker(type = "addCustomerToCrm")
    public void addCustomerToCrm(@Variable String customerName) {
        System.out.println("Adding customer to CRM via REST: " + customerName);

        String request = "someData";
        restTemplate.put(ENDPOINT_CRM, request);
    }

}
