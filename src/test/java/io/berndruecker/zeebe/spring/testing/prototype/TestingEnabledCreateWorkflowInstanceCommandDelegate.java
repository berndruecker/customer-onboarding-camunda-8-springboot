package io.berndruecker.zeebe.spring.testing.prototype;

import io.camunda.zeebe.client.api.ZeebeFuture;
import io.camunda.zeebe.client.api.command.CreateProcessInstanceCommandStep1;
import io.camunda.zeebe.client.api.command.FinalCommandStep;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import io.camunda.zeebe.client.impl.command.CreateProcessInstanceCommandImpl;

import java.io.InputStream;
import java.time.Duration;
import java.util.List;
import java.util.Map;

public class TestingEnabledCreateWorkflowInstanceCommandDelegate implements CreateProcessInstanceCommandStep1, CreateProcessInstanceCommandStep1.CreateProcessInstanceCommandStep2, CreateProcessInstanceCommandStep1.CreateProcessInstanceCommandStep3 {

    private CreateProcessInstanceCommandImpl delegate;

    public TestingEnabledCreateWorkflowInstanceCommandDelegate(CreateProcessInstanceCommandImpl delegate) {
        this.delegate = delegate;
    }

    @Override
    public CreateProcessInstanceCommandStep2 bpmnProcessId(String bpmnProcessId) {
        delegate.bpmnProcessId(bpmnProcessId);
        return this;
    }

    @Override
    public CreateProcessInstanceCommandStep3 processDefinitionKey(long workflowKey) {
        delegate.processDefinitionKey(workflowKey);
        return this;
    }

    @Override
    public CreateProcessInstanceCommandStep3 version(int version) {
        delegate.version(version);
        return this;
    }

    @Override
    public CreateProcessInstanceCommandStep3 latestVersion() {
        delegate.latestVersion();
        return this;
    }

    @Override
    public CreateProcessInstanceCommandStep3 variables(InputStream variables) {
        delegate.variables(variables);
        return this;
    }

    @Override
    public CreateProcessInstanceCommandStep3 variables(String variables) {
        delegate.variables(variables);
        return this;
    }

    @Override
    public CreateProcessInstanceCommandStep3 variables(Map<String, Object> variables) {
        delegate.variables(variables);
        return this;
    }

    @Override
    public CreateProcessInstanceCommandStep3 variables(Object variables) {
        delegate.variables(variables);
        return this;
    }

    @Override
    public CreateProcessInstanceWithResultCommandStep1 withResult() {
        throw new RuntimeException("Not yet implemented!!");
    }

    @Override
    public FinalCommandStep<ProcessInstanceEvent> requestTimeout(Duration requestTimeout) {
        delegate.requestTimeout(requestTimeout);
        return this;
    }

    @Override
    public ZeebeFuture<ProcessInstanceEvent> send() {
        ZeebeFuture<ProcessInstanceEvent> future = delegate.send();
        ZeebeTestRecorder.add(future);
        return future;
    }

}
