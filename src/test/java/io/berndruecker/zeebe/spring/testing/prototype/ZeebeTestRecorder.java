package io.berndruecker.zeebe.spring.testing.prototype;

import io.zeebe.client.api.ZeebeFuture;
import io.zeebe.client.api.response.WorkflowInstanceEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

public class ZeebeTestRecorder {

    private static ExecutorService executor = Executors.newSingleThreadExecutor();

    private static List<Future> futures = new ArrayList<>();

    public static List<WorkflowInstanceEvent> startedWorkflowInstances = new ArrayList<>();
    public static List<RecordedJob> polledJobs = new ArrayList<>();

    public static void reset() {
        startedWorkflowInstances = new ArrayList<>();
        polledJobs = new ArrayList<>();
        futures = new ArrayList<>();
    }

    public static Optional<RecordedJob> getNextPolledJobsForTaskType(String taskType) {
        return polledJobs.stream() //
            .filter( job -> job.getJob().getType().equals(taskType) ) //
            .findFirst();
    }

    public static void add(final ZeebeFuture<WorkflowInstanceEvent> future) {
        futures.add(future);
        executor.execute(() -> {
            WorkflowInstanceEvent evt = future.join();
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


}
