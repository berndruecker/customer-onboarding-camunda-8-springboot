package io.berndruecker.onboarding.customer.process;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;

@Component
public class ScoringAdapter {

    private static Logger logger = LoggerFactory.getLogger(ScoringAdapter.class);

    @ZeebeWorker(type = "scoreCustomer", autoComplete = true)
    public Map<String, Object> scoreCustomer() {
        HashMap<String, Object> resultVariables = new HashMap<>();

        logger.info("score...");
        resultVariables.put("score", 42);

        return resultVariables;
    }

}
