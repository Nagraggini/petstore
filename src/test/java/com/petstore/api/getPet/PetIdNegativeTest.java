package com.petstore.api.getPet;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class PetIdNegativeTest {

    @Test
    void petCreationWithMissingNameTest() {
        // Szövegblokk. Bemeneti adatok.
        String requestBody = """
                {
                	"id":"46476453521523456345342315234186"
                }
                """;

        given()
                .body(requestBody)
                .when().post("/pet")
                .then()
                .statusCode(201)
                .body("id", equalTo("46476453521523456345342315234186"))
                .body("&", not(hasKey("name")));// Nem tartalmaz name mezőt a válasz.

        // Utána töröld is az állatot.
        // TODO
    }
}
