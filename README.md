## Purpose

This rest-assured-demo example contains an API automation framework using Java and Rest Assured to test the Restful
Booker API at https://restful-booker.herokuapp.com

It includes positive and negative tests including schema validation, github actions with Allure reports published to github pages and applies DRY principles.

## Getting the Latest Code

To get the latest code open your IDE terminal and navigate to where you would like the project to reside (usually a dev folder) and run `git clone https://github.com/druoid/rest-assured-demo.git`

## Setting Up the Environment

To run Rest Assured tests locally, your system must have Java installed and be properly configured. Here's what you need in terms of Java setup:

1. Install Java, (Java 17 Jdk is a common LTS choice).
2. After installing, you must set the JAVA_HOME environment variable and update your PATH (It's different if your on windows or mac so I won't give a how to here)
4. Open the project in your IDE of choice  
5. Wait for the project to build

## Running the Tests

To run the tests from your IDE terminal run the following command `mvn clean test` from the project root.

## Viewing the Allure reports locally

To view the Allure reports locally Allure Commandline (CLI) must be installed (It's different if your on windows or mac so I won't give a how to here).  


After running the tests run the command `allure serve allure-results` from the project root and Allure will start up a web server where you can view the results.

## Viewing the Allure reports on Github

To view the Allure reports in the rest-assured-demo repo navigate to the `Deployments` section and click on the git-hub pages link. 

This will take you to the git-hub pages deployments page where you can click on the latest deployment and see the latest test run as well as a history of test runs.