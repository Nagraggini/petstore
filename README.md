## Rest API tests for https://petstore.swagger.io/

This repository contains example tests for sample Petstore server.

# Test Report
![Allure Report](/docs/assets/img/allure_report.png.png)

# Toolbox

- Test automation framework: JUnit 5
- Reporting: Allure Report, Surefire Report, Log4j
- API client/testing framework: rest-assured, POJO
- Build tool: Maven
- Java 1.8+

In order to run the tests execute:

On Linux:
```./mvnw clean test```

On Windows:
```mvnw clean test```

After execution completes, report will be available in ```target/surefire-reports/html/index.html```