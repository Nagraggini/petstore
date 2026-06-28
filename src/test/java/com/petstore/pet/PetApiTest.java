package com.petstore.pet;

import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.petstore.base.BaseTest;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;

public class PetApiTest extends BaseTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    /**
     * Rigorous Test :-)
     */
    @Test
    void getUserByUsername() {
        given()
                .when()
                .get("/pet/1")
                .then()
                .statusCode(200).body("id", notNullValue());
    }
}
