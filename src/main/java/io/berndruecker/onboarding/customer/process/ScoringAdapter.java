package io.berndruecker.onboarding.customer.process;

import io.zeebe.client.api.response.ActivatedJob;
import io.zeebe.client.api.worker.JobClient;
import io.zeebe.spring.client.annotation.ZeebeWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

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
