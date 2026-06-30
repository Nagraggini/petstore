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
                assertEquals(response.getStatusCode(), 200);

                /*
                 * System.out.println("===== STATUS =====");
                 * System.out.println(response.getStatusCode());
                 * System.out.println("------------------------------------");
                 * System.out.println("getStatusLine: \n" + response.getStatusLine());
                 * 
                 * System.out.println("\n===== HEADERS =====");
                 * System.out.println("getHeaders: \n" + response.getHeaders());
                 * System.out.println("------------------------------------");
                 * System.out.println("content-type: \n" + response.getHeader("content-type"));
                 * 
                 * System.out.println("\n===== BODY =====");
                 * System.out.println("getBody(): \n" + response.getBody().asString());
                 * 
                 * System.out.println("\n===== RESPONSE TIME =====");
                 * System.out.println(response.getTime() + " ms");
                 * System.out.println("------------------------------------");
                 * System.out.println(response.jsonPath().getInt("id"));
                 * System.out.println(response.jsonPath().getString("name"));
                 * System.out.println(response.jsonPath().getString("status"));
                 * 
                 * 
                 * 
                 * System.out.println("------------------------------------");
                 * 
                 * given()
                 * .when()
                 * .get("https://petstore.swagger.io/v2/pet/5")
                 * .then()
                 * .log().all();
                 */
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
                assertTrue(response.jsonPath().getString("name").contains("dog"));
                assertEquals("string", response.jsonPath().getString("status"));
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
                // getList("$") → teljes lista
                assertTrue(response.jsonPath().getList("$").size() > 0);
        }

}
