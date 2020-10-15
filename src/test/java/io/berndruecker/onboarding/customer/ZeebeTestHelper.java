package io.berndruecker.onboarding.customer;

import io.berndruecker.zeebe.spring.testing.prototype.RecordedJob;
import io.berndruecker.zeebe.spring.testing.prototype.ZeebeTestRecorder;
import io.zeebe.client.ZeebeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Component
public class ZeebeTestHelper {

    @Autowired
    private ZeebeClient client;

    public void reset() {
        ZeebeTestRecorder.reset();
    }

    public RecordedJob assertAndExecuteJob(String taskType) throws Exception {
        Optional<RecordedJob> recordedJob = ZeebeTestRecorder.getNextPolledJobsForTaskType(taskType);
        assertTrue(recordedJob.isPresent(), "Job for taskType '" + taskType + "' is present");
        RecordedJob job = recordedJob.get();

        // TODO: Does Hand over that job client lead to problems?
        job.getDelegate().handle(job.getClient(), job.getJob());

        ZeebeTestRecorder.polledJobs.remove(job);
        return job;
    }

    public void assertProcessInstanceStarted() {
        assertTrue( ZeebeTestRecorder.startedWorkflowInstances.size() > 0 );
    }

    public void waitForOngoingWork() {
        System.out.println("WAITING FOR WORK");
        ZeebeTestRecorder.waitForOngoingWork();
        System.out.println("DONE WAITING FOR WORK");
    }
}
