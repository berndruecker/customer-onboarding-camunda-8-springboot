package io.berndruecker.zeebe.spring.testing.prototype;

import io.zeebe.client.api.response.ActivatedJob;
import io.zeebe.client.api.worker.JobClient;
import io.zeebe.client.api.worker.JobHandler;

public class RecordedJob {

    private JobClient client;
    private ActivatedJob job;
    private JobHandler delegate;

    public RecordedJob(JobClient client, ActivatedJob job, JobHandler delegate) {
        this.client = client;
        this.job = job;
        this.delegate = delegate;
    }

    public JobClient getClient() {
        return client;
    }

    public ActivatedJob getJob() {
        return job;
    }

    public JobHandler getDelegate() {
        return delegate;
    }
}
