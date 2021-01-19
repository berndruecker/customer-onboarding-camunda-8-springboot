package io.berndruecker.onboarding.customer.dmn;

import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.camunda.bpm.dmn.engine.impl.DefaultDmnEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DmnConfig {

    @Bean
    public DmnEngine createDmnEngine() {

        final DefaultDmnEngineConfiguration config =
                (DefaultDmnEngineConfiguration)
                        DmnEngineConfiguration.createDefaultDmnEngineConfiguration();
        return config.buildEngine();
    }
}