package com.petstore.base;

import org.junit.jupiter.api.BeforeAll;

import io.restassured.RestAssured;

public class BaseTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

}
