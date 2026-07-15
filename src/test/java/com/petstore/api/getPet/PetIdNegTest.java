package com.petstore.api.getPet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.Test;

import com.petstore.api.base.BaseApiTest;

import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import static io.restassured.path.xml.XmlPath.from;

public class PetIdNegTest extends BaseApiTest {

    /**
     * TC2.2 Nem létező kisállat lekérése
     * előfeltétel:
     * Létező kisállat (id=999)
     * lépések:
     * Küld GET kérést a /users/999
     * elvárt eredmény:
     * http státuszkód: 404
     * A válasz body egy üres json objektum {}
     */
    @Test
    void petIdUnsuccesfulTest() {
        String expectedMessage = "Pet not found";

        // 1. Kérés küldése és válasz elmentése
        String responseBody = given()
                .accept(ContentType.XML)
                .pathParam("petId", "999")
                .when()
                .get("pet/{petId}")
                .then()
                .log().ifValidationFails()
                .statusCode(404)
                .extract().body().asString();

        // 2. Az XML válasz manuális validálása
        assertThat(from(responseBody).getString("apiResponse.message"), equalTo(expectedMessage));

        // Ha lenne a válasz, akkor: json kulcsérték pár megvalósítás, ami igazából a
        // map.

        // A válasz body egy üres json objektum {}
        // Akkor ez kell: .body("$", anEmptyMap())
    }
}
