package com.petstore.api.postPet;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;
import org.junit.jupiter.api.Test;

import com.petstore.api.base.BaseApiTest;

import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

public class PostPetNegativeTest extends BaseApiTest {

    /**
     * TC3.2 - Kisállat létrehozása hiányos adatokkal (POST)
     * előfeltétel: még nem létező kisállat
     * bemeneti adat:
     * csak a nevet adjuk meg
     * name: “Killer”
     * lépések:
     * Küldj POST kérést a /pet végpontra a megadott adatokkal (request body)
     * elvárt eredmény:
     * státuszkód: 200
     * a válasz body nem tartalmaz status mezőt
     */
    @Test
    void petCreationWithOnlyNameTest() {
        // Szövegblokk. Bemeneti adatok.
        String requestBody = """
                        {
                "name":"Killer"}
                        """;
        given().contentType(ContentType.JSON).body(requestBody).when().post("/pet").then()
                .statusCode(200).body("name", equalTo("Killer")).body("$", hasKey("id"))
                .body("$", not(hasKey("status"))); // not(..) -> nem tartalmaz valamit.
    }

}
