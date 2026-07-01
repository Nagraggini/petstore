package com.petstore.pet;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.petstore.base.BaseTest;

import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class PetApiPractisingTest extends BaseTest {

    /**
     * Kérd le a petet ID = 5. Ellenőrizd, hogy a státuszkód 200
     */
    @Test
    void getOneIdAndCheckStatusCode() {
        int petId = 5;
        given().pathParam("petId", petId).when().get("/pet/{petId}").then().statusCode(200);
    }

    /**
     * Kérd le a petet ID = 5. Írasd ki a teljes JSON választ
     */
    @Test
    void getPetByIdAndWriteAllResponseToTheConsole() {
        int petId = 5;
        // given().pathParam("petId",
        // petId).when().get("/pet/{petId}").then().log().all();
    }

    /**
     * Kérd le a petet ID = 5. Olvasd ki a name mezőt, és írasd ki a konzolra.
     */
    @Test
    void getPetByIdAndWriteNameToTheConsole() {
        int petId = 5;
        Response response = given().pathParam("petId", petId).when().get("/pet/{petId}");
        System.out.println("name: " + response.jsonPath().getString("name"));
    }

    /**
     * Kérd le a petet ID = 5. Ellenőrizd, hogy a status értéke "available"
     */
    @Test
    void getPetByIdAndCheckAvailableStatus() {
        int petId = 5;
        Response response = given().pathParam("petId", petId).when().get("/pet/{petId}");
        assertFalse(response.jsonPath().getString("status").equals("available"));
    }

    /**
     * Kérd le a petet ID = 10. Használd a pathParam()-ot, és ne írd bele kézzel az
     * URL-be.
     */
    @Test
    void getPetByIdAndUsePathParam() {
        int petId = 10;
        Response response = given().pathParam("petId", petId).when().get("/pet/{petId}");
    }

    /**
     * Kérd le a "available" státuszú peteket, queryParam-ot használj.
     */
    @Test
    void getAvailableStatusPets() {
        String status = "available";

        Response response = given().pathParam("status", status).when().get("/pet/findByStatus?status={status}");
        assertTrue(response.jsonPath().getList("$").size() > 0);
    }

    /**
     * 7. feladat – Lista méret
     * 
     * Ugyanaz a /findByStatus kérés.
     * 
     * Feladat:
     * 
     * írasd ki, hány petet kaptál vissza
     */
    @Test
    void findByAvailableStatusAndCountThem() {
        String status = "available";

        Response response = given().pathParam("status", status).when().get("/pet/findByStatus?status={status}");
        System.out.println(" " + response.jsonPath().getList("$").size() + " db elérhető kisállat");
    }

    /**
     * 8. feladat – Első elem
     * 
     * A /findByStatus válaszból:
     * 
     * Feladat:
     * 
     * írasd ki az első pet:
     * id
     * name
     * status
     */
    @Test
    void getfindByAvailableStatusAndWriteFirstPet() {
        String status = "available";

        Response response = given()
                .queryParam("status", status)
                .when()
                .get("/pet/findByStatus");

        response.then().statusCode(200).body("[0].name", equalTo("Dog-1782907715799"));

        List<Map<String, Object>> pets = response.jsonPath().getList("$");

        System.out.println("-----------------------");
        System.out.println("Az első elérhető kisállat (id,name,status):" + pets.get(0));
        System.out.println("-----------------------");
    }

}