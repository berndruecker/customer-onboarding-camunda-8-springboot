package io.berndruecker.zeebe.spring.testing.prototype;

import io.zeebe.client.api.worker.JobHandler;
import io.zeebe.client.api.worker.JobWorker;
import io.zeebe.client.api.worker.JobWorkerBuilderStep1;
import io.zeebe.client.impl.worker.JobWorkerBuilderImpl;

import java.time.Duration;
import java.util.List;

public class TestingEnabledJobWorkerBuilderDelegate implements JobWorkerBuilderStep1, JobWorkerBuilderStep1.JobWorkerBuilderStep2, JobWorkerBuilderStep1.JobWorkerBuilderStep3 {

    private JobWorkerBuilderImpl delegate;

    public TestingEnabledJobWorkerBuilderDelegate (JobWorkerBuilderImpl delegate) {
        this.delegate = delegate;
    }

    @Override
    public JobWorkerBuilderStep2 jobType(String type) {
        delegate.jobType(type);
        return this;
    }

    @Override
    public JobWorkerBuilderStep3 handler(JobHandler handler) {
        delegate.handler(new TestingEnabledJobHandlerDelegate(handler));
        return this;
    }

    @Override
    public JobWorkerBuilderStep3 timeout(long timeout) {
        delegate.timeout(timeout);
        return this;
    }

    @Override
    public JobWorkerBuilderStep3 timeout(Duration timeout) {
        delegate.timeout(timeout);
        return this;
    }

    @Override
    public JobWorkerBuilderStep3 name(String workerName) {
        delegate.name(workerName);
        return this;
    }

    @Override
    public JobWorkerBuilderStep3 maxJobsActive(int maxJobsActive) {
        delegate.maxJobsActive(maxJobsActive);
        return this;
    }

    @Override
    public JobWorkerBuilderStep3 pollInterval(Duration pollInterval) {
        delegate.pollInterval(pollInterval);
        return this;
    }

    @Override
    public JobWorkerBuilderStep3 requestTimeout(Duration requestTimeout) {
        delegate.requestTimeout(requestTimeout);
        return this;
    }

    @Override
    public JobWorkerBuilderStep3 fetchVariables(List<String> fetchVariables) {
        delegate.fetchVariables(fetchVariables);
        return this;
    }

    @Override
    public JobWorkerBuilderStep3 fetchVariables(String... fetchVariables) {
        delegate.fetchVariables(fetchVariables);
        return this;
    }

    @Override
    public JobWorker open() {
        return delegate.open();
    }
}
