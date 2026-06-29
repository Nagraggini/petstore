package com.petstore.pet;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.petstore.base.BaseTest;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class PetApiTest extends BaseTest {

        /**
         * Trying it.
         */
        @Test
        @DisplayName("Trying it.")
        void tryTest() {
                Response response = get("https://petstore.swagger.io/v2/pet/5");
                response.getStatusCode();
                response.getTime();

                System.out.println("response.getBody().asString(): " + response.getBody().asString());
                // response.getBody().asString():
                // {"id":2,"name":"Doggie","photoUrls":[],"tags":[],"status":"available"}
                System.out.println("getStatusLine: " + response.getStatusLine());
                System.out.println("content-type: " + response.getHeader("content-type"));

                assertEquals(response.getStatusCode(), 200);
        }

        /**
         * 2 Trying it.
         */
        @Test
        @DisplayName("2 Trying it.")
        void try2Test() {
                baseURI = "https://petstore.swagger.io/v2";

                given().queryParam("status", "pending").then().statusCode(200).body("id[1]",
                                equalTo("NewName")).log().all();

        }

        /**
         * Returns a single pet by id.
         */
        @Test
        @DisplayName("Returns a single pet by id.")
        void getPetByPetId() {
                int petId = 5;

                Response response = given()
                                .pathParam("petId", petId)
                                .when()
                                .get("/pet/{petId}");

                response.then()
                                .statusCode(200);

                assertEquals(petId, response.jsonPath().getInt("id"));
                assertEquals("doggie", response.jsonPath().getString("name"));
                assertEquals("string", response.jsonPath().getString("status"));

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
