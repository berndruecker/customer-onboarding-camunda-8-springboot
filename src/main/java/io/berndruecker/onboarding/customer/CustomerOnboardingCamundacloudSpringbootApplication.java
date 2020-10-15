package io.berndruecker.onboarding.customer;

import io.zeebe.spring.client.EnableZeebeClient;
import io.zeebe.spring.client.annotation.ZeebeDeployment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableZeebeClient
@ZeebeDeployment(classPathResources = { "customer-onboarding.bpmn", "customer-scoring.bpmn" })
public class CustomerOnboardingCamundacloudSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerOnboardingCamundacloudSpringbootApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
