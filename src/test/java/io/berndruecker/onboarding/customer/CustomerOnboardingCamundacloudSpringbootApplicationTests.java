package io.berndruecker.onboarding.customer;

import com.sun.tools.jconsole.JConsoleContext;
import io.berndruecker.onboarding.customer.rest.CustomerOnboardingRestController;
import io.berndruecker.zeebe.spring.testing.prototype.RecordedJob;
import io.zeebe.client.ZeebeClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CustomerOnboardingCamundacloudSpringbootApplicationTests {

//	@Autowired
//	private ZeebeClient client;

	@Autowired
	CustomerOnboardingRestController customerOnboardingRest;

	@Autowired
	private ZeebeTestHelper test;

	@Test
	void contextLoads() {
	}

	@Test
	void testHappyPath() throws Exception {
//		client.newCreateInstanceCommand().bpmnProcessId("customer-onboarding").latestVersion().send().join();
		// retrieving an incoming REST call
		customerOnboardingRest.onboardCustomer();

		// assert that a process was started and will send the AMQP message now
		test.waitForOngoingWork();
		test.assertProcessInstanceStarted();
		RecordedJob job = test.assertAndExecuteJob("SendScoringRequest");
		assertEquals("TaskSendScoringRequest", job.getJob().getElementId());
		assertEquals("customer-scoring", job.getJob().getBpmnProcessId());

		// mock AMQP

		// Simulate the AMQP response

		// assert that the decision has passed and resulted in "automaticProcessing"

		// assert that the process is now in "Add Customer to CRM" & "Add Customer To Billing"

	}

}
