package com.petstore.api.postPet;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.petstore.api.base.BaseApiTest;

import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PostPetTest extends BaseApiTest {

        /**
         * TC3.1 - új kisállat létrehozása (POST)
         * előfeltétel: még nem létező felhasználó
         * bemeneti adat:
         * id: “Teszt Elek”
         * name: “Spaghetti”
         * lépések:
         * Küldj POST kérést a /pet végpontra a megadott adatokkal (request body)
         * elvárt eredmény:
         * státuszkód: 201
         * a válasz body tartalmaz id mezőt
         * a válasz body tartalmaz name mezőt, értékük a bemeneti adattal
         * egyező. Nem tartalmaz status mezőt.
         */
        @Test
        void petSuccessCreatedTest() {

                int id = 999;

                // Előtte törlöm a kisállatot, ha létezik.
                given().when().delete("/pet/" + id).then().log().ifValidationFails()
                                .statusCode(anyOf(is(200), is(404)));

                // Szövegblokk. Bemeneti adatokat vesszővel kell elválasztani. Az id szám mező,
                // ezért nem kell idézőjelek közé rakni.
                String requestBody = """
                                {
                                "id": 999,
                                "name":"Spaghetti"
                                }
                                """;

                given().contentType(ContentType.JSON)
                                .body(requestBody)
                                .when().post("/pet")
                                .then()
                                .log().ifValidationFails()
                                .statusCode(200)
                                .body("id", equalTo(
                                                id))
                                .body("name", equalTo(
                                                "Spaghetti"))
                                .body("$", not(hasKey("status")));// Nem tartalmaz status mezőt a válasz.

                // Nem tárolja el az adatokat a JSON placeholder.

                // Utána törlöm a kisállatot.
                given().when().delete("/pet/" + id).then().log().ifValidationFails()
                                .statusCode(anyOf(is(200), is(404)));
        }

        // Ha szükségünk van a válaszban érkező valamelyik adatokra későbbi feldolgozás
        // végett, akkor extract
        // és path használatával kimenthető az adat:
        @Test
        void petSuccessCreatedTestWithExract() {

                int id = 999;

                // Előtte törlöm a kisállatot, ha létezik.
                given().when().delete("/pet/" + id).then().log().ifValidationFails()
                                .statusCode(anyOf(is(200), is(404)));

                // Szövegblokk. Bemeneti adatokat vesszővel kell elválasztani. Az id szám mező,
                // ezért nem kell idézőjelek közé rakni.
                String requestBody = """
                                {
                                "id": 999,
                                "name":"Spaghetti"
                                }
                                """;

                int newID = given()
                                .contentType(ContentType.JSON) // Jelezzük, hogy kérés (request) JSON tartalmú.
                                .body(requestBody) // Tartalma pedig a bemeneti adatokat tartalmazó szövegblokk.
                                .when()
                                .post("/pet")
                                .then()
                                .statusCode(200)
                                .body("id", notNullValue())
                                .body("name", equalTo("Spaghetti"))
                                .extract()
                                .path("id"); // id-t kivesszük

                // Utána is törlöm.
                given().when().delete("/pet/" + id).then().log().ifValidationFails()
                                .statusCode(anyOf(is(200), is(404)));
        }

        // Ha a teljes választ akarnánk feldolgozni, akkor Response-ként kell
        // letároljuk.
        @Test
        void petSuccessCreatedTestWithResponse() {

                int id = 999;

                // Előtte törlöm a kisállatot, ha létezik.
                given().when().delete("/pet/" + id).then().log().ifValidationFails()
                                .statusCode(anyOf(is(200), is(404)));

                // Szövegblokk. Bemeneti adatokat vesszővel kell elválasztani. Az id szám mező,
                // ezért nem kell idézőjelek közé rakni.
                String requestBody = """
                                {
                                "id": 999,
                                "name":"Spaghetti"
                                }
                                """;

                Response responseObj = given()
                                .contentType(ContentType.JSON) // Jelezzük, hogy kérés (request) JSON tartalmú.
                                .body(requestBody) // Tartalma pedig a bemeneti adatokat tartalmazó szövegblokk.
                                .when()
                                .post("/pet")
                                .then()
                                .statusCode(200)
                                .body("id", notNullValue())
                                .body("name", equalTo("Spaghetti"))
                                .extract()
                                .response(); // Teljes response-t eltároljuk.

                int newId = responseObj.path("id");

                // Utána is törlöm.
                given().when().delete("/pet/" + id).then().log().ifValidationFails()
                                .statusCode(anyOf(is(200), is(404)));
        }

        // Ha a teljes válasz objektumként tárolva van, a then elhagyható és külön
        // assert-ekkel tesztelhető
        @Test
        void petSuccessCreatedTestWithoutThen() {

                int id = 999;

                // Előtte törlöm a kisállatot, ha létezik.
                given().when().delete("/pet/" + id).then().log().ifValidationFails()
                                .statusCode(anyOf(is(200), is(404)));

                // Szövegblokk. Bemeneti adatokat vesszővel kell elválasztani. Az id szám mező,
                // ezért nem kell idézőjelek közé rakni.
                String requestBody = """
                                {
                                "id": 999,
                                "name":"Spaghetti"
                                }
                                """;

                Response responseObj = given()
                                .contentType(ContentType.JSON) // Jelezzük, hogy kérés (request) JSON tartalmú.
                                .body(requestBody) // Tartalma pedig a bemeneti adatokat tartalmazó szövegblokk.
                                .when()
                                .post("/pet");

                int newID = responseObj.path("id");

                assertEquals(200, responseObj.statusCode());
                assertEquals(id, newID);
                assertEquals("Spaghetti", responseObj.path("name"));

                // Utána is törlöm.
                given().when().delete("/pet/" + id).then().log().ifValidationFails()
                                .statusCode(anyOf(is(200), is(404)));
        }
}
