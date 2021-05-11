# Customer Onboarding Process

*Process solution example for customer onboarding as used in the OReilly book [Practical Process Automation](https://processautomationbook.com/).*

This following stack is used:

* Camunda Cloud
* Java
* Spring Boot

# Under Construction Warning

This example contains some workarounds for for features missing in Camunda Cloud (they are on the near term roadmap):

* A custom form for the user task to approve customer orders is not yet designed, using the generic form in the tasklist
* The project implements a bespoke DMN Worker because the DMN integration is not yet out-of-the-box
* Automated unit tests are not yet implemented

# Intro

This simple onboarding process is meant to get started with process automation, workflow engines and BPMN:

![Customer Onboarding](docs/customer-onboarding-simple.png)

The process model contains three tasks:

* A service task that executes Java Code to score customers (using the stateless Camunda DMN engine)
* A user task so that humans can approve customer orders (or not)
* A service task that executes glue code to call the REST API of a CRM system

The process solution is a Maven project and contains:

* The onboarding process model as BPMN
* Source code to provide a REST endpoint for clients
* Java code to do the customer scoring
* Glue code to implement the REST call to the CRM system
* Fake for CRM system providing a REST API that can be called (to allow running this example self-contained)


# How To Run
https://youtu.be/
<a href="http://www.youtube.com/watch?feature=player_embedded&v=QUB0dSBBMPM" target="_blank"><img src="http://img.youtube.com/vi/QUB0dSBBMPM/0.jpg" alt="Walkthrough" width="240" height="180" border="10" /></a>

## Create Camunda Cloud Cluster

* Login to https://camunda.io/ (you can create an account on the fly)
* Create a new Zeebe cluster (any type of cluster will do)
* Create a new set of API client credentials
* Copy the client credentials into `src/main/resources/application.properties`


## Run Spring Boot Java Application

The application will deploy the process model during startup

`mvn package exec:java`


## Play

You can easily use the application by requesting a new customer onboarding posting a PUT REST request to 

`curl -X PUT http://localhost:8080/customer`

You can now see the process instance in Camunda Operate - linked via the Cloud Console.

You can work on the user task using Camunda Tasklist, also linked via the Cloud Console.



# Extended Process

There is also an extended process model that adds some more tasks in the process: 

![Customer Onboarding](docs/customer-onboarding-extended.png)

You can find that in another repository on GitHub: https://github.com/berndruecker/customer-onboarding-camundacloud-springboot-extended