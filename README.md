## REST API Tests: Swagger Petstore 

This repository contains an example test suite for the [Swagger Petstore API](https://petstore.swagger.io/). It serves as a practical implementation of API test automation.

Toolbox:

- Test automation framework: JUnit
- Reporting: Maven Surefire Reports
- API client/testing framework: rest-assured
- Build tool: Maven
- Java 8 or later

Requirements:
- JDK 8+
- Maven 3.x
- An active internet connection is required to reach the Petstore API.

To run the tests, execute:

```mvn clean test```

To run a single test:                  

```mvn -Dtest=PetApiPractisingTest#getOneIdAndCheckStatusCode test```

After execution completes, report will be available in ```target/surefire-reports/html/index.html```