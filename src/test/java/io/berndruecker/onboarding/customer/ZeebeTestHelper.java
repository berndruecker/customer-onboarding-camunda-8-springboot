package io.berndruecker.onboarding.customer;

import io.berndruecker.zeebe.spring.testing.prototype.RecordedJob;
import io.berndruecker.zeebe.spring.testing.prototype.ZeebeTestRecorder;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Component
public class ZeebeTestHelper {

    @Autowired
    private ZeebeClient client;

    public void reset() {
        ZeebeTestRecorder.reset();
    }

    public RecordedJob assertAndExecuteJob(ProcessInstanceEvent workflowInstance, String taskType) throws Exception {
        // TODO: WAIT FOR AT LEAST ONE JOB TO ARRIVE IN A CERTAIN TIMEFRAME
        Optional<RecordedJob> recordedJob = ZeebeTestRecorder.waiForJob(workflowInstance, taskType);

        assertTrue(recordedJob.isPresent(), "Job for taskType '" + taskType + "' is present");
        RecordedJob job = recordedJob.get();

        // TODO: Does Hand over that job client lead to problems?
        job.getDelegate().handle(job.getClient(), job.getJob());

        ZeebeTestRecorder.remove(job);
        return job;
    }

    public ProcessInstanceEvent assertProcessInstanceStarted() {
        ZeebeTestRecorder.waitForOngoingWork();

        assertTrue( ZeebeTestRecorder.startedWorkflowInstances().size() > 0 );
        return ZeebeTestRecorder.startedWorkflowInstances().stream().findFirst().get();
    }

}
