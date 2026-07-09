package com.petstore.api.getPet;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import org.junit.jupiter.api.Test;

import com.petstore.api.base.BaseApiTest;

import static io.restassured.RestAssured.given;

public class getPets extends BaseApiTest {

    @Test
    void checkOnePet() {
        int petId = 1;

        given().get("/pet/{petId}").then().statusCode(200).body("name", equalTo("Doggie")).body("name",
                hasItems("Doggie", "Dogs"));

    }

    @Test
    void checkMultiplePets() {
        given()
                .queryParam("status", "available")
                .when()
                .get("/pet/findByStatus")
                .then()
                .statusCode(200)
                // Ellenőrzi, hogy a visszaadott listában van-e "Doggie" és "Fishy" nevű állat
                .body("name", hasItems("Doggie", "Fishy"));
    }

    @Test
    void checkOnePetTags() {
        int petId = 1;

        given()
                .pathParam("petId", petId)
                .when()
                .get("/pet/{petId}")
                .then()
                .statusCode(200)
                .body("name", equalTo("Doggie"))
                // A "tags.name" kigyűjti az összes címke nevét egy listába
                .body("tags.name", hasItems("friendly", "dog"));
    }

}
