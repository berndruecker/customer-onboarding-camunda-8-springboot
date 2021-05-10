package io.berndruecker.onboarding.customer;

import io.berndruecker.onboarding.customer.rest.CustomerOnboardingRestController;
import io.berndruecker.zeebe.spring.testing.prototype.TestingEnabledZeebeClientBuilderDelegate;
import io.camunda.zeebe.spring.client.ZeebeClientObjectFactory;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.ZeebeClientBuilder;
import io.camunda.zeebe.client.impl.ZeebeClientBuilderImpl;
import io.camunda.zeebe.client.impl.ZeebeClientImpl;
import io.camunda.zeebe.spring.client.ZeebeClientLifecycle;
import io.camunda.zeebe.spring.client.ZeebeClientObjectFactory;
import io.camunda.zeebe.spring.client.bean.value.factory.ReadAnnotationValueConfiguration;
import io.camunda.zeebe.spring.client.config.processor.PostProcessorConfiguration;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import java.util.Properties;

import static org.mockito.Mockito.mock;

/**
 * Overwrite certain configurations to allow for testing
 */
@Configuration
@EnableAutoConfiguration(exclude={RabbitAutoConfiguration.class})
//@TestConfiguration
public class TestingEnabledZeebeClientConfiguration {

    @Autowired
    ZeebeClientBuilder zeebeClientBuilder;

    @Bean
    @Primary
    public ZeebeClientObjectFactory decoratedZeebeClientObjectFactory() {
        return () -> (ZeebeClient) new TestingEnabledZeebeClientBuilderDelegate(zeebeClientBuilder).build();
    }

}
