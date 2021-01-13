package io.berndruecker.zeebe.spring.testing.prototype;

import io.grpc.ClientInterceptor;
import io.zeebe.client.CredentialsProvider;
import io.zeebe.client.ZeebeClient;
import io.zeebe.client.ZeebeClientBuilder;
import io.zeebe.client.ZeebeClientConfiguration;
import io.zeebe.client.impl.ZeebeClientBuilderImpl;

import java.time.Duration;
import java.util.Properties;

public class TestingEnabledZeebeClientBuilderDelegate implements ZeebeClientBuilder { //}, ZeebeClientConfiguration {

    private ZeebeClientBuilder delegate;

    public TestingEnabledZeebeClientBuilderDelegate(ZeebeClientBuilder delegate) {
        this.delegate = delegate;
    }

    @Override
    public ZeebeClientBuilder withProperties(Properties properties) {
        delegate.withProperties(properties);
        return this;
    }

    @Override
    public ZeebeClientBuilder brokerContactPoint(String contactPoint) {
        delegate.brokerContactPoint(contactPoint);
        return this;
    }

    @Override
    public ZeebeClientBuilder gatewayAddress(String gatewayAddress) {
        delegate.gatewayAddress(gatewayAddress);
        return this;
    }

    @Override
    public ZeebeClientBuilder defaultJobWorkerMaxJobsActive(int maxJobsActive) {
        delegate.defaultJobWorkerMaxJobsActive(maxJobsActive);
        return this;
    }

    @Override
    public ZeebeClientBuilder numJobWorkerExecutionThreads(int numThreads) {
        delegate.numJobWorkerExecutionThreads(numThreads);
        return this;
    }

    @Override
    public ZeebeClientBuilder defaultJobWorkerName(String workerName) {
        delegate.defaultJobWorkerName(workerName);
        return this;
    }

    @Override
    public ZeebeClientBuilder defaultJobTimeout(Duration timeout) {
        delegate.defaultJobTimeout(timeout);
        return this;
    }

    @Override
    public ZeebeClientBuilder defaultJobPollInterval(Duration pollInterval) {
        delegate.defaultJobPollInterval(pollInterval);
        return this;
    }

    @Override
    public ZeebeClientBuilder defaultMessageTimeToLive(Duration timeToLive) {
        delegate.defaultMessageTimeToLive(timeToLive);
        return this;
    }

    @Override
    public ZeebeClientBuilder defaultRequestTimeout(Duration requestTimeout) {
        delegate.defaultRequestTimeout(requestTimeout);
        return this;
    }

    @Override
    public ZeebeClientBuilder usePlaintext() {
        delegate.usePlaintext();
        return this;
    }

    @Override
    public ZeebeClientBuilder caCertificatePath(String certificatePath) {
        delegate.caCertificatePath(certificatePath);
        return this;
    }

    @Override
    public ZeebeClientBuilder credentialsProvider(CredentialsProvider credentialsProvider) {
        delegate.credentialsProvider(credentialsProvider);
        return this;
    }

    @Override
    public ZeebeClientBuilder keepAlive(Duration keepAlive) {
        delegate.keepAlive(keepAlive);
        return this;
    }

    @Override
    public ZeebeClientBuilder withInterceptors(ClientInterceptor... interceptor) {
        delegate.withInterceptors(interceptor);
        return this;
    }

    @Override
    public ZeebeClient build() {
        return new TestingEnabledZeebeClientDelegate( delegate.build() );
    }
}
