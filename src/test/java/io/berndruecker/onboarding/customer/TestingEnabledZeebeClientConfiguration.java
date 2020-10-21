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
import org.mockito.Mock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

    @Mock
    private RabbitTemplate rabbitTemplate = mock(RabbitTemplate.class);

    @Bean
    protected RabbitTemplate rabbitTemplate() {
        return rabbitTemplate;
    }


}
