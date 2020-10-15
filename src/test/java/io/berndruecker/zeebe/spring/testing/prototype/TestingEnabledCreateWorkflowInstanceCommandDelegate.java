package io.berndruecker.zeebe.spring.testing.prototype;

import io.zeebe.client.api.ZeebeFuture;
import io.zeebe.client.api.command.CreateWorkflowInstanceCommandStep1;
import io.zeebe.client.api.command.FinalCommandStep;
import io.zeebe.client.api.response.WorkflowInstanceEvent;
import io.zeebe.client.impl.command.CreateWorkflowInstanceCommandImpl;

import java.io.InputStream;
import java.time.Duration;
import java.util.List;
import java.util.Map;

public class TestingEnabledCreateWorkflowInstanceCommandDelegate implements CreateWorkflowInstanceCommandStep1, CreateWorkflowInstanceCommandStep1.CreateWorkflowInstanceCommandStep2, CreateWorkflowInstanceCommandStep1.CreateWorkflowInstanceCommandStep3 {

    private CreateWorkflowInstanceCommandImpl delegate;

    public TestingEnabledCreateWorkflowInstanceCommandDelegate(CreateWorkflowInstanceCommandImpl delegate) {
        this.delegate = delegate;
    }

    @Override
    public CreateWorkflowInstanceCommandStep2 bpmnProcessId(String bpmnProcessId) {
        delegate.bpmnProcessId(bpmnProcessId);
        return this;
    }

    @Override
    public CreateWorkflowInstanceCommandStep3 workflowKey(long workflowKey) {
        delegate.workflowKey(workflowKey);
        return this;
    }

    @Override
    public CreateWorkflowInstanceCommandStep3 version(int version) {
        delegate.version(version);
        return this;
    }

    @Override
    public CreateWorkflowInstanceCommandStep3 latestVersion() {
        delegate.latestVersion();
        return this;
    }

    @Override
    public CreateWorkflowInstanceCommandStep3 variables(InputStream variables) {
        delegate.variables(variables);
        return this;
    }

    @Override
    public CreateWorkflowInstanceCommandStep3 variables(String variables) {
        delegate.variables(variables);
        return this;
    }

    @Override
    public CreateWorkflowInstanceCommandStep3 variables(Map<String, Object> variables) {
        delegate.variables(variables);
        return this;
    }

    @Override
    public CreateWorkflowInstanceCommandStep3 variables(Object variables) {
        delegate.variables(variables);
        return this;
    }

    @Override
    public CreateWorkflowInstanceWithResultCommandStep1 withResult() {
        throw new RuntimeException("Not yet implemented!!");
    }

    @Override
    public FinalCommandStep<WorkflowInstanceEvent> requestTimeout(Duration requestTimeout) {
        delegate.requestTimeout(requestTimeout);
        return this;
    }

    @Override
    public ZeebeFuture<WorkflowInstanceEvent> send() {
        ZeebeFuture<WorkflowInstanceEvent> future = delegate.send();
        ZeebeTestRecorder.add(future);
        return future;
    }

}
