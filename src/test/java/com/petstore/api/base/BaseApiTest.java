package com.petstore.api.base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeAll;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;

public class BaseApiTest {

    protected static PrintStream fileOut;

    @BeforeAll
    public static void setup() throws FileNotFoundException {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        // Ez egy alapbeállítás az összes teszthez.
        RestAssured.requestSpecification = RestAssured
                .given()
                .accept(ContentType.JSON);

        File directory = new File("logs");
        if (!directory.exists()) {
            directory.mkdir();
        }

        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
        String filePath = "logs/test-" + timeStamp + ".log";

        // Az osztályszintű változó inicializálása
        fileOut = new PrintStream(new FileOutputStream(filePath));

        // Filterek beállítása egyszer
        RestAssured.filters(
                new RequestLoggingFilter(fileOut),
                new ResponseLoggingFilter(fileOut));

    }

    // Segédmetódus a log fájl "elválasztásához"
    protected void logTestHeader(String testName) {
        fileOut.println("\n" + "=".repeat(20));
        fileOut.println("TESZT KEZDETE: " + testName);
        fileOut.println("=".repeat(20) + "\n");
    }
}
