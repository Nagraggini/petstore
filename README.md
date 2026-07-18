## REST API Tests: Swagger Petstore 

This repository contains an example test suite for the [Swagger Petstore API](https://petstore.swagger.io/). It serves as a practical implementation of API test automation.

# Test Report
![Allure Report](/docs/assets/img/allure_report.png)

# Toolbox

- Test automation framework: JUnit
- Reporting: Maven Surefire Reports
- API client/testing framework: rest-assured
- Build tool: Maven
- Java 15 or later

Requirements:
- JDK 8+
- Maven 3.x
- An active internet connection is required to reach the Petstore API.

To run the tests, execute:

On Linux:
```./mvnw clean test```

On Windows:
```mvnw clean test```

To run a single test:                  

```./mvnw -Dtest=PetIdPozitiveTest#checkOnePet test```

After execution completes, report will be available in ```/allure-report/index.html```