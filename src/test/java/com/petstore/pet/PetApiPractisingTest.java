package com.petstore.pet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

import com.petstore.base.BaseTest;

import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class PetApiPractisingTest extends BaseTest {

    /**
     * Kérd le a petet ID = 5. Ellenőrizd, hogy a státuszkód 200
     */
    @Test
    void one() {
        int petId = 5;
        given().pathParam("petId", petId).when().get("/pet/{petId}").then().statusCode(200);
    }

    /**
     * Kérd le a petet ID = 5. Írasd ki a teljes JSON választ
     */
    @Test
    void two() {
        int petId = 5;
        given().pathParam("petId", petId).when().get("/pet/{petId}").then().log().all();
    }

    /**
     * Kérd le a petet ID = 5. Olvasd ki a name mezőt, és írasd ki a konzolra.
     */
    @Test
    void three() {
        int petId = 5;
        Response response = given().pathParam("petId", petId).when().get("/pet/{petId}");
        System.out.println("name: " + response.jsonPath().getString("name"));
    }

    /**
     * Kérd le a petet ID = 5. Ellenőrizd, hogy a status értéke "available"
     */
    @Test
    void four() {
        int petId = 5;
        Response response = given().pathParam("petId", petId).when().get("/pet/{petId}");
        assertFalse(response.jsonPath().getString("status").equals("available"));
    }

    /**
     * Kérd le a petet ID = 10. Használd a pathParam()-ot, és ne írd bele kézzel az
     * URL-be.
     */
    @Test
    void five() {
        int petId = 10;
        Response response = given().pathParam("petId", petId).when().get("/pet/{petId}");
    }

    /**
     * Kérd le a "available" státuszú peteket, queryParam-ot használj.
     */
    @Test
    void six() {
        String status = "available";

    }
    /*
     * 7. feladat – Lista méret
     * 
     * Ugyanaz a /findByStatus kérés.
     * 
     * Feladat:
     * 
     * írasd ki, hány petet kaptál vissza
     * 
     * 🟡 8. feladat – Első elem
     * 
     * A /findByStatus válaszból:
     * 
     * Feladat:
     * 
     * írasd ki az első pet:
     * id
     * name
     * status
     * 
     * 🟠 9. feladat – Contains ellenőrzés
     * 
     * Kérd le a peteket "pending" státusszal.
     * 
     * Feladat:
     * 
     * ellenőrizd, hogy az első pet neve tartalmazza a "dog" szót
     * 
     * 🟠 10. feladat – Headers kiírás
     * 
     * Kérd le a petet ID = 5.
     * 
     * Feladat:
     * 
     * írasd ki az összes HTTP headert
     * 
     * 🟠 11. feladat – Content-Type ellenőrzés
     * 
     * Kérd le a petet ID = 5.
     * 
     * Feladat:
     * 
     * ellenőrizd, hogy:
     * Content-Type = application/json
     * 
     * 🔴 12. feladat – Hibás pet
     * 
     * Kérd le a petet ID = 999999
     * 
     * Feladat:
     * 
     * ellenőrizd, hogy:
     * 404-es státuszkód jön
     * 
     * 🔴 13. feladat – Hibaüzenet
     * 
     * A 999999-es pet esetén:
     * 
     * Feladat:
     * 
     * olvasd ki:
     * code
     * type
     * message
     * 
     * 🔴 14. feladat – Minden status ellenőrzése
     * 
     * findByStatus?status=pending
     * 
     * Feladat:
     * 
     * ellenőrizd, hogy MINDEN visszakapott pet:
     * status == "pending"
     * 
     * 🔥 15. feladat – Mini challenge
     * 
     * Kombináld:
     * 
     * queryParam
     * lista kezelése
     * assert
     * 
     * Feladat:
     * 
     * kérd le az "available" peteket
     * írasd ki:
     * hány darab van
     * az első és utolsó pet neve
     */
}
