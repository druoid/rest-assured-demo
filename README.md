## Purpose

This rest-assured-demo example contains an API automation framework using Java and Rest Assured to test the Restful
Booker API at https://restful-booker.herokuapp.com

## Getting the Latest Code

To get the latest code open your IDE terminal and navigate to where you would like the project to reside (usually a dev folder) and run `git clone https://github.com/druoid/rest-assured-demo.git`

## Setting Up the Environment

To run Rest Assured tests locally, your system must have Java installed and be properly configured. Here's what you need in terms of Java setup:

1. Install Java, (Java 17 Jdk is a common LTS choice).
2. After installing, you must set the JAVA_HOME environment variable and update your PATH (It's different if your on windows or mac so I won't give a how to here)
4. Open the project in your IDE of choice  
5. Wait for the project to build

## Running the Tests

To run the tests from your IDE terminal run the following command `mvn clean test -D allure.results.directory=target/allure-results`

## Additional Details

After tests have run...
To view the generated reports locally run `allure serve target/allure-results`

To view the generated reports from CI, download the allure-report artifact from the Github action and unzip. 
If on a mac move the allure-report folder out of your downloads folder i.e macOS restricts Terminal and other CLI tools from accessing downloads
Navigate to the new location for the allure-report folder in your terminal and run the command `npx serve .`
Install the serve package as prompted and then navigate to `http://localhost:3000` - The report will be generated at this local server address 

## Logging

Logging examples are only shown for demonstration purposes on the CreateBookingTest.java file

## Other
Using VS code I was finding that running `mvn clean test` would generate the allure-results in the root of the project.  The additional `-D allure.results.directory=target/allure-results` appended
ensures that allure-results are generated in the target folder.
