package io.berndruecker.zeebe.spring.testing.prototype;

import io.camunda.zeebe.client.api.ZeebeFuture;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;

import javax.annotation.meta.When;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.fail;

public class ZeebeTestRecorder {

    private static ExecutorService executor = Executors.newSingleThreadExecutor();

    private static List<Future> futures = new ArrayList<>();

    private static List<ProcessInstanceEvent> startedWorkflowInstances = new ArrayList<>();
    private static List<RecordedJob> polledJobs = new ArrayList<>();

    private static Semaphore jobSemaphore = new Semaphore(0);

    public static void reset() {
        startedWorkflowInstances = new ArrayList<>();
        polledJobs = new ArrayList<>();
        futures = new ArrayList<>();
        jobSemaphore = new Semaphore(0);
    }

    public static Optional<RecordedJob> waiForJob(ProcessInstanceEvent workflowInstance, String taskType) {
        System.out.println("WAITING FOR '" + taskType + "' in instance " + workflowInstance);

        Optional<RecordedJob> recordedJob = polledJobs.stream().filter( job -> //
                    job.getJob().getType().equals(taskType) && (workflowInstance == null || job.getJob().getProcessInstanceKey() == workflowInstance.getProcessInstanceKey())) //
            .findFirst();

        while (recordedJob.isEmpty()) {
            System.out.println("Acquiring semaphore...");
            try {
                if (!jobSemaphore.tryAcquire(30, TimeUnit.SECONDS)) {
                    fail("Could not get job of type '"+taskType+"' for " + workflowInstance + " within 30 seconds time");
                };
            } catch (InterruptedException e) {
                throw new RuntimeException("Could not acquire sempahore", e);
            }
            System.out.println("   ...done");
            recordedJob = polledJobs.stream().filter( job -> //
                    job.getJob().getType().equals(taskType) && (workflowInstance == null || job.getJob().getProcessInstanceKey() == workflowInstance.getProcessInstanceKey())) //
                    .findFirst();
        }
        return recordedJob;
    }

    public static void add(final RecordedJob job) {
        polledJobs.add(job);
        System.out.println("Added job " + job.getJob());
        jobSemaphore.release();
    }
    public static void remove(RecordedJob job) {
        polledJobs.remove(job);
    }

    public static void add(final ZeebeFuture<ProcessInstanceEvent> future) {
        futures.add(future);
        executor.execute(() -> {
            ProcessInstanceEvent evt = future.join();
            startedWorkflowInstances.add(evt);
        });
    }

    public static void waitForOngoingWork() {
        try {
            for (Future future : futures) {
                if (!future.isDone()) {
                    future.get();
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Exception while waiting for futures to complete: " + e.getMessage(), e);
        }
    }


    public static List<RecordedJob> polledJobs() {
        return polledJobs;
    }


    public static List<ProcessInstanceEvent> startedWorkflowInstances() {
        return startedWorkflowInstances;
    }

}
