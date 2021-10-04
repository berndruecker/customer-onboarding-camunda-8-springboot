package io.berndruecker.onboarding.customer.process;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;

@Component
public class ScoringAdapter {

    Logger logger = LoggerFactory.getLogger(ScoringAdapter.class);

    @ZeebeWorker(type = "scoreCustomer")
    public void scoreCustomer(final JobClient client, final ActivatedJob job) throws IOException {
        logger.info("score...");

        client.newCompleteCommand(job.getKey()) //
                .send().join();
    }

}
