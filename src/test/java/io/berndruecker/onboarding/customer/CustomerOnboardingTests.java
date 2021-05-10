package io.berndruecker.onboarding.customer;

import io.berndruecker.onboarding.customer.rest.CustomerOnboardingRestController;
import io.berndruecker.zeebe.spring.testing.prototype.RecordedJob;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest(properties = {
		"restProxyHost=api.example.org", //
		"restProxyPort=80" })
class CustomerOnboardingTests {

	@Autowired
	private CustomerOnboardingRestController customerOnboardingRest;

	@Autowired
	private ZeebeTestHelper test;

	@Autowired
	private RestTemplate restTemplate;
	private MockRestServiceServer mockRestServer;

	@Autowired
	private CamundaCloudManagement cloudManagement;

	@BeforeEach
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockRestServer = MockRestServiceServer.createServer(restTemplate);

		// Setup cluster?
		// Takes around 2 minutes
		// TODO: Create and use a Zeebe client that is using that cluster credentials now!
		String clusterId = cloudManagement.createCluster();
	}

	@AfterEach
	public void tearDown() throws Exception {
		cloudManagement.deleteCurrentCluster();
	}

		@Test
	void contextLoads() {
	}

	@Test
	void testHappyPath() throws Exception {
		// receiving an incoming REST call that will kick off a new process instance
		customerOnboardingRest.onboardCustomer();

		// assert that a process was started
		ProcessInstanceEvent workflowInstance = test.assertProcessInstanceStarted();

		// Do Scoring
		RecordedJob job = test.assertAndExecuteJob(null, "SendScoringRequest");
		assertEquals("TaskSendScoringRequest", job.getJob().getElementId());
		assertEquals("customer-scoring", job.getJob().getBpmnProcessId());

		// DMN Decision
		job = test.assertAndExecuteJob(workflowInstance, "DMN");

		// assert that the decision has passed and resulted in "automaticProcessing"
		// LIMIT: Can't verify this!
		//test.assertTaskCompleted("TaskValidateCustomerApplication");
		//test.assertProcessVariableEquals("automaticProcessing", true);

		// expect the customer to be added to CRM via REST
		mockRestServer
				.expect(requestTo("http://localhost:8080/crm/customer")) //
				.andExpect(method(HttpMethod.PUT))
//			.andExpect(jsonPath("amount").value("547"))
				.andRespond(withSuccess("{\"transactionId\": \"12345\"}", MediaType.APPLICATION_JSON));

		// expect the customer to be added to billing via REST
		mockRestServer
				.expect(requestTo("http://localhost:8080/billing/customer")) //
				.andExpect(method(HttpMethod.PUT))
//			.andExpect(jsonPath("amount").value("547"))
				.andRespond(withSuccess("{\"transactionId\": \"12345\"}", MediaType.APPLICATION_JSON));

		// assert that the process is now in "Add Customer to CRM" & "Add Customer To Billing"
		job = test.assertAndExecuteJob(workflowInstance,"addCustomerToCrm");
		job = test.assertAndExecuteJob(workflowInstance,"addCustomerToBilling");

		// TODO continue....

	}


}
