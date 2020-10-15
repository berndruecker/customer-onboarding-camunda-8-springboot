package io.berndruecker.onboarding.customer.process;

import io.berndruecker.onboarding.customer.ProcessConstants;
import io.zeebe.client.api.response.ActivatedJob;
import io.zeebe.client.api.worker.JobClient;
import io.zeebe.spring.client.annotation.ZeebeWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;

@Component
public class CustomerOnboardingGlueCode {

    Logger logger = LoggerFactory.getLogger(CustomerOnboardingGlueCode.class);

    // This should be of course injected and depends on the environment.
    // Hard coded for simplicity here
    public static String ENDPOINT = "http://localhost:8080/crm/customer";

    @Autowired
    private RestTemplate restTemplate;

    @ZeebeWorker(type = "addCustomerToCrm")
    public void addCustomerToCrmViaREST(final JobClient client, final ActivatedJob job) throws IOException {
        logger.info("Add customer to CRM via REST [" + job + "]");

        // call rest API
        String request = "todo";
        restTemplate.put(ENDPOINT, request);

        client.newCompleteCommand(job.getKey()) //
                .send().join();
    }

}
