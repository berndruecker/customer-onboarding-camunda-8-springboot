package io.berndruecker.onboarding.customer;

import io.berndruecker.onboarding.customer.rest.CustomerOnboardingRestController;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.process.test.api.ZeebeTestEngine;
import io.camunda.zeebe.process.test.inspections.InspectionUtility;
import io.camunda.zeebe.process.test.inspections.model.InspectedProcessInstance;
import io.camunda.zeebe.spring.test.ZeebeSpringTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import static io.camunda.zeebe.process.test.assertions.BpmnAssert.assertThat;
import static io.camunda.zeebe.protocol.Protocol.USER_TASK_JOB_TYPE;
import static io.camunda.zeebe.spring.test.ZeebeTestThreadSupport.waitForProcessInstanceCompleted;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ZeebeSpringTest
public class TestCustomerOnboardingProcess {

    @Autowired
    private CustomerOnboardingRestController customerOnboardingRestController;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockRestServer;

    @BeforeEach
    public void init() {
        // Class level @AutoConfigureMockRestServiceServer does not work for me, so initializing it manually
        mockRestServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void testAutomaticOnboarding() throws Exception {
        // Define expectations on the REST calls
        // 1. http://localhost:8080/crm/customer
        mockRestServer
                .expect(requestTo("http://localhost:8080/crm/customer")) //
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withSuccess("{\"customerId\": \"12345\"}", MediaType.APPLICATION_JSON));

        // given a REST call
        customerOnboardingRestController.onboardCustomer();

        // Retrieve process instances started because of the above call
        InspectedProcessInstance processInstance = InspectionUtility.findProcessInstances().findLastProcessInstance().get();

        // We expect to have a user task
        waitForUserTaskAndComplete("TaskApproveCustomerOrder", Collections.singletonMap("approved", true));

        // Now the process should run to the end
        waitForProcessInstanceCompleted(processInstance, Duration.ofSeconds(10));

        // Let's assert that it passed certain BPMN elements (more to show off features here)
        assertThat(processInstance)
                .hasPassedElement("EndEventProcessed")
                .isCompleted();

        // And verify it caused the right side effects on the REST endpoints
        mockRestServer.verify();
    }

    /**
     * This code should eventually be provided by spring-zeebe-test
     */

    @Autowired
    private ZeebeTestEngine zeebeTestEngine;
    @Autowired
    private ZeebeClient zeebeClient;

    public void waitForUserTaskAndComplete(String userTaskId) throws InterruptedException, TimeoutException {
        waitForUserTaskAndComplete(userTaskId, new HashMap<>());
    }

    public void waitForUserTaskAndComplete(String userTaskId, Map<String, Object> variables) throws InterruptedException, TimeoutException {
        // Let the workflow engine do whatever it needs to do
        zeebeTestEngine.waitForIdleState(Duration.ofSeconds(10));

        // Now get all user tasks
        List<ActivatedJob> jobs = zeebeClient.newActivateJobsCommand().jobType(USER_TASK_JOB_TYPE).maxJobsToActivate(1).workerName("waitForUserTaskAndComplete").send().join().getJobs();

        // Should be only one
        assertTrue(jobs.size()>0, "Job for user task '" + userTaskId + "' does not exist");
        ActivatedJob userTaskJob = jobs.get(0);
        // Make sure it is the right one
        if (userTaskId!=null) {
            assertEquals(userTaskId, userTaskJob.getElementId());
        }

        // And complete it passing the variables
        if (variables!=null && variables.size()>0) {
            zeebeClient.newCompleteCommand(userTaskJob.getKey()).variables(variables).send().join();
        } else {
            zeebeClient.newCompleteCommand(userTaskJob.getKey()).send().join();
        }
    }


}
