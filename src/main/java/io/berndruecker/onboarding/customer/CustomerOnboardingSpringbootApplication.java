package io.berndruecker.onboarding.customer;

import io.camunda.zeebe.spring.client.EnableZeebeClient;
import io.camunda.zeebe.spring.client.annotation.Deployment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Deployment(resources = "classpath:customer-onboarding.bpmn")
public class CustomerOnboardingSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerOnboardingSpringbootApplication.class, args);
	}


}
