package io.berndruecker.onboarding.customer;

import io.berndruecker.onboarding.customer.process.TwitterConnector;
import io.camunda.zeebe.spring.client.EnableZeebeClient;
import io.camunda.zeebe.spring.client.annotation.Deployment;
import io.camunda.zeebe.spring.client.annotation.ZeebeDeployment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableZeebeClient
@Deployment(resources = "classpath:customer-onboarding.bpmn")
public class CustomerOnboardingSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerOnboardingSpringbootApplication.class, args);
	}

	/*
	@Bean
	public TwitterConnector twitterConnector() {
		return new TwitterConnector();
	}
	*/
}
