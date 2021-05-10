package io.berndruecker.zeebe.spring.testing.prototype;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;

public class TestingEnabledJobHandlerDelegate implements JobHandler {

    private JobHandler delegate;

    public TestingEnabledJobHandlerDelegate(JobHandler delegate) {
        this.delegate = delegate;
    }

    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {
        ZeebeTestRecorder.add( new RecordedJob(client, job, delegate) );
    }
}
