package io.berndruecker.onboarding.customer;

import io.berndruecker.onboarding.customer.rest.CustomerOnboardingRestController;
import io.berndruecker.zeebe.spring.testing.prototype.TestingEnabledZeebeClientBuilderDelegate;
import io.zeebe.client.ZeebeClient;
import io.zeebe.client.ZeebeClientBuilder;
import io.zeebe.client.impl.ZeebeClientBuilderImpl;
import io.zeebe.client.impl.ZeebeClientImpl;
import io.zeebe.spring.client.ZeebeClientLifecycle;
import io.zeebe.spring.client.ZeebeClientObjectFactory;
import io.zeebe.spring.client.bean.value.factory.ReadAnnotationValueConfiguration;
import io.zeebe.spring.client.config.processor.PostProcessorConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import java.util.Properties;

/**
 * Overwrite certain configurations to allow for testing
 */
@Configuration
public class TestingEnabledZeebeClientConfiguration {

    @Autowired
    ZeebeClientBuilder zeebeClientBuilder;

    @Bean
    @Primary
    public ZeebeClientObjectFactory decoratedZeebeClientObjectFactory() {
        return () -> (ZeebeClient) new TestingEnabledZeebeClientBuilderDelegate(zeebeClientBuilder).build();
    }

}
