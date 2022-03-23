package io.berndruecker.onboarding.customer.process;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
public class CustomerOnboardingGlueCode {

    private static Logger logger = LoggerFactory.getLogger(CustomerOnboardingGlueCode.class);

    // This would be of course injected and depends on the environment. Hard coded for now
    public static String ENDPOINT_CRM = "http://localhost:8080/crm/customer";

    @Autowired
    private RestTemplate restTemplate;

    @ZeebeWorker(type = "addCustomerToCrm", autoComplete = true)
    public void addCustomerToCrmViaREST(final ActivatedJob job) throws IOException {
        logger.info("Add customer to CRM via REST [" + job + "]");

        // call rest API
        String request = "someData";
        restTemplate.put(ENDPOINT_CRM, request);
    }

}
