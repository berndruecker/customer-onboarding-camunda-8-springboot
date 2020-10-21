package io.berndruecker.onboarding.customer.process;

import io.zeebe.client.api.response.ActivatedJob;
import io.zeebe.client.api.worker.JobClient;
import io.zeebe.client.api.worker.JobHandler;
import io.zeebe.spring.client.annotation.ZeebeWorker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionResult;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.model.dmn.Dmn;
import org.camunda.bpm.model.dmn.DmnModelInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DmnJobHandler {

    private Logger logger = LoggerFactory.getLogger(DmnJobHandler.class);
    private static final String DECISION_ID_HEADER = "decisionRef";

    @Autowired
    private DmnEngine dmnEngine;

    @Value("classpath*:*.dmn")
    private Resource[] resources;
    private final Map<String, DmnDecision> decisionsById = new HashMap<>();

    @ZeebeWorker(type = "DMN")
    public void handleDecisionTask(JobClient client, ActivatedJob job) {
        final DmnDecision decision = findDecisionForTask(job);
        final Map<String, Object> variables = job.getVariablesAsMap();

        final DmnDecisionResult decisionResult = dmnEngine.evaluateDecision(decision, variables);

        client.newCompleteCommand(job.getKey()) //
            .variables(Collections.singletonMap("result", decisionResult)) //
            .send();
    }

    private DmnDecision findDecisionForTask(ActivatedJob job) {
        final String decisionId = job.getCustomHeaders().get(DECISION_ID_HEADER);
        if (decisionId == null || decisionId.isEmpty()) {
            throw new RuntimeException("Missing header: '" + DECISION_ID_HEADER + "'");
        }

        final DmnDecision decision = decisionsById.get(decisionId);
        if (decision == null) {
            throw new RuntimeException(String.format("No decision found with id: '%s'", decisionId));
        }
        return decision;
    }

    @PostConstruct
    public void readDmnModelsFromClasspath() {
        logger.info("Load DMN files from classpath: {}", resources);
        for (Resource dmnResource: resources) {
            try {
                DmnModelInstance dmnModel = Dmn.readModelFromStream(dmnResource.getInputStream());
                dmnEngine.parseDecisions(dmnModel).forEach( //
                        decision -> {
                            logger.info(
                                    "Found decision with id '{}' in file: {}",
                                    decision.getKey(),
                                    dmnResource);
                            decisionsById.put(decision.getKey(), decision);
                        });
            } catch (Throwable t) {
                logger.warn("Failed to parse decision: {}", dmnResource, t);
            }
        }
    }
}

