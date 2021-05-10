package io.berndruecker.zeebe.spring.testing.prototype;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.ZeebeClientConfiguration;
import io.camunda.zeebe.client.api.command.*;
import io.camunda.zeebe.client.api.worker.JobWorkerBuilderStep1;
import io.camunda.zeebe.client.impl.command.CreateProcessInstanceCommandImpl;
import io.camunda.zeebe.client.impl.worker.JobWorkerBuilderImpl;

public class TestingEnabledZeebeClientDelegate implements ZeebeClient {

    private ZeebeClient client;

    public TestingEnabledZeebeClientDelegate(ZeebeClient client) {
        this.client = client;
    }

    @Override
    public TopologyRequestStep1 newTopologyRequest() {
        return client.newTopologyRequest();
    }

    @Override
    public ZeebeClientConfiguration getConfiguration() {
        return client.getConfiguration();
    }

    @Override
    public void close() {
        client.close();
    }

    @Override
    public DeployProcessCommandStep1 newDeployCommand() {
        return client.newDeployCommand();
    }

    @Override
    public CreateProcessInstanceCommandStep1 newCreateInstanceCommand() {
        return new TestingEnabledCreateWorkflowInstanceCommandDelegate( (CreateProcessInstanceCommandImpl)client.newCreateInstanceCommand() );
    }

    @Override
    public CancelProcessInstanceCommandStep1 newCancelInstanceCommand(long workflowInstanceKey) {
        return client.newCancelInstanceCommand(workflowInstanceKey);
    }

    @Override
    public SetVariablesCommandStep1 newSetVariablesCommand(long elementInstanceKey) {
        return client.newSetVariablesCommand(elementInstanceKey);
    }

    @Override
    public PublishMessageCommandStep1 newPublishMessageCommand() {
        return client.newPublishMessageCommand();
    }

    @Override
    public ResolveIncidentCommandStep1 newResolveIncidentCommand(long incidentKey) {
        return client.newResolveIncidentCommand(incidentKey);
    }

    @Override
    public UpdateRetriesJobCommandStep1 newUpdateRetriesCommand(long jobKey) {
        return client.newUpdateRetriesCommand(jobKey);
    }

    @Override
    public JobWorkerBuilderStep1 newWorker() {
        return new TestingEnabledJobWorkerBuilderDelegate( (JobWorkerBuilderImpl)client.newWorker() );
    }

    @Override
    public ActivateJobsCommandStep1 newActivateJobsCommand() {
        return client.newActivateJobsCommand();
    }

    @Override
    public CompleteJobCommandStep1 newCompleteCommand(long jobKey) {
        return client.newCompleteCommand(jobKey);
    }

    @Override
    public FailJobCommandStep1 newFailCommand(long jobKey) {
        return client.newFailCommand(jobKey);
    }

    @Override
    public ThrowErrorCommandStep1 newThrowErrorCommand(long jobKey) {
        return client.newThrowErrorCommand(jobKey);
    }
}
