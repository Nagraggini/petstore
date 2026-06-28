package com.petstore.pet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.petstore.base.BaseTest;

import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class PetApiTest extends BaseTest {

    /**
     * Returns a single pet by id.
     */
    @Test
    @DisplayName("Returns a single pet by id.")
    void getPetByPetId() {
        int petId = 3;

        Response response = given()
                .pathParam("petId", petId)
                .when()
                .get("/pet/{petId}");

        response.then()
                .statusCode(200);

        assertEquals(petId, response.jsonPath().getInt("id"));
        assertEquals("bbre", response.jsonPath().getString("name"));
        assertEquals("sold", response.jsonPath().getString("status"));
        System.out.println("StatusCode: " + response.getStatusCode() + "; getTime: " + response.getTime());
    }

    /**
     * Returns a not found pet by id.
     */
    @Test
    @DisplayName("Returns a not found pet by id.")
    void getPetByPetIdNegativeTest() {
        int petId = 0;

        Response response = given()
                .pathParam("petId", petId)
                .when()
                .get("/pet/{petId}");

        response.then()
                .statusCode(404);

        assertEquals(1, response.jsonPath().getInt("code"));
        assertEquals("error", response.jsonPath().getString("type"));
        assertEquals("Pet not found", response.jsonPath().getString("message"));
    }

    /**
     * Returns a all pets by status.
     */
    @Test
    @DisplayName("Returns a all pets by status.")
    void getPetByPetStatus() {
        String status = "pending";

        Response response = given()
                .pathParam("status",
                        status)
                .when()
                .get("/pet/findByStatus?status={status}");

        response.then()
                .statusCode(200);

        assertTrue(response.jsonPath().getString("status").contains(status));

    }

}
