package com.petstore.api.getPet;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.petstore.api.base.BaseApiTest;
import com.petstore.model.Pet;

import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class PetIdPozitiveTest extends BaseApiTest {

    // import org.apache.logging.log4j.Logger;
    // A végén lévő zárójelben magát az osztályt kell megadni.
    private static final Logger LOG = LogManager.getLogger(PetIdPozitiveTest.class);

    /**
     * TC1.1 a randomId1 azonosítójú kisállat lekérése
     * előfeltétel: létező kisállat
     * lépés: GET kérés küldése a /pet/1 végponthoz
     * elvárt eredmény: http státuszkód: 200; válasz nem null
     * A válasz tartalmazza az id=1 azonosítót és a név mezőt.
     */
    @Test
    void checkOnePet() {
        long id = createdPetIds.get(0);

        Response responseObject = given().get("/pet/{id}", id).then().log()
                .ifValidationFails() // A jó log, csak baj esetén szól (ha pl.: nem létező státuszt adunk
                                     // meg).statusCode(200)
                .body("id", notNullValue())
                .body("status", equalTo("available"))
                .extract().response(); // Lehet az extract() után .path("id") is.

        long responseId = responseObject.path("id");
        String name = responseObject.path("name");

        assertTrue(name.contains("Doggie_"));
        assertEquals(id, responseId);
    }

    @Test
    void checkOnePetWithoutThen() {
        long id = createdPetIds.get(0);
        String nameExpected = "Doggie_" + createdPetIds.get(0);

        // Szövegblokk. Bemeneti adatok.
        String requestBody = """
                {
                    "id": %d,
                    "name": "%s",
                }
                """.formatted(id, nameExpected);

        Response responseObject = given().get("/pet/{id}", id);

        long responseId = responseObject.path("id");
        String name = responseObject.path("name");

        assertTrue(name.contains(nameExpected));
        assertEquals(id, responseId);
    }

    @Test
    void petIdSuccessTestToPetObject() {

        long id = createdPetIds.get(0);

        /* Mivel a Jackson be lett importálva, ő képes jsonból objektumot generálni. */

        Pet petObj = given().when().get("/pet/" + id).then().statusCode(200).extract().as(Pet.class);

        /*
         * A .as(Pet.class) hívás mögött a Jackson JSON könyvtár dolgozik.
         * Ő végzi el a deszerializálást: a JSON mezőit hozzárendeli
         * a User osztály mezőihez.
         */

        assertEquals(id, petObj.getId());
        assertTrue(petObj.getName().length() > 0);
        assertNotNull(petObj.getStatus());
    }

    @Test
    void checkMultiplePets() {
        given()
                .queryParam("status", "available")
                .when()
                .get("/pet/findByStatus")
                .then()
                .log().ifValidationFails() // A jó log, csak baj esetén szól (ha pl.: nem létező státuszt adunk meg)
                .statusCode(200)
                // Ellenőrzi, hogy a visszaadott listában megvan-e mindkét állat.
                .body("name", hasItems(("Doggie_" + createdPetIds.get(0)), "Doggie_" + createdPetIds.get(3)));
    }

    @Test
    void checkOnePetTag() {

        Response responseObject = given()
                .when()
                .get("/pet/{id}", createdPetIds.get(1))
                .then()
                .log().ifValidationFails() // A jó log, csak baj esetén szól (ha pl.: nem létező státuszt adunk meg)
                .statusCode(200)
                .body("name", containsString("Doggie"))
                // A "tags.name" kigyűjti az összes címke nevét egy listába
                .body("tags.name", hasItems("MyCustomTag")).extract().response();

        List<String> tagNames = responseObject.path("tags.name");
        assertEquals("MyCustomTag", tagNames.get(0));
    }

}
