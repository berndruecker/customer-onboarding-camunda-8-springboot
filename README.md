# Customer Onboarding Process

*Process solution example for customer onboarding as used in the OReilly book [Practical Process Automation](https://processautomationbook.com/).*

This following stack is used:

* Camunda Cloud
* Java
* Spring Boot

# Under Construction Warning

This example is still under construction (as we are still approaching Camunda Cloud GA), so it might not work as expected out-of-the-box for you just yet.

It also contains some workarounds for for features missing in Camunda Cloud (but that are on the near term roadmap):

* User Task & Form for the user to approve customer orders are missing, simulated by service task simply completing user tasks
* Own DMN Worker because DMN Integration doesn#t yet work out of the box

# Into

The simple process is meant to get started with process automation, workflow engines and BPMN:

![Customer Onboarding](docs/customer-onboarding-simple.png)

The process model contains three tasks:

* A service task that executes Java Code to score customers
* A user task so that humans can approve customer orders (or not)
* A service task that executes glue code to call the REST API of a CRM system

The process solution is a Maven project and contains:

* The onboarding process model as BPMN
* Source code to provide the REST API for clients (using Spring Boot)
* Java code to do the customer scoring
* Glue code to implement the REST call to the CRM system
* Fake for CRM system providing a REST API that can be called (to allow running this example self-contained)


# How to run

* Create a cluster and copy API credentials (see https://github.com/berndruecker/ticket-booking-camunda-cloud#create-camunda-cloud-cluster, TODO: IMPROVE)

* Run Spring Boot Java application, it will deploy the process model during startup

`mvn package exec:java`

* Test by requesting a new customer onboarding 

`curl -X PUT http://localhost:8080/customer`

* Check in Operate



# Extended Process

There is also an extended process model that adds some more tasks in the process: 

![Customer Onboarding](docs/customer-onboarding-extended.png)

You can find that in another repository on GitHub: https://github.com/berndruecker/customer-onboarding-camundacloud-springboot-extended