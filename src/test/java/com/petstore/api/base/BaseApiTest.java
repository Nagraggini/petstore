package com.petstore.api.base;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

public class BaseApiTest {

    protected List<Long> createdPetIds = new ArrayList<>();

    @BeforeEach
    public void setupDatas() {
        createdPetIds.clear();
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        // Ez egy alapbeállítás az összes teszthez.
        RestAssured.requestSpecification = RestAssured
                .given()
                .accept(ContentType.JSON) // json formátumú választ várunk
                .contentType(ContentType.JSON); // json formátumban küldjük a kérést

        // Ezzel lehet kiírni a konzolra az összes infót.
        // RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        String tagName = "MyCustomTag";
        // A 4 kívánt státusz definiálása
        String[] statuses = { "available", "pending", "sold", "available" };

        // Ciklus 4 állat létrehozásához (a státuszok száma alapján)
        for (int i = 0; i < statuses.length; i++) {
            long randomId = Math.abs(UUID.randomUUID().getMostSignificantBits());
            createdPetIds.add(randomId);

            String name = "Doggie_" + randomId;
            String currentStatus = statuses[i]; // Az aktuális státusz kivétele a tömbből

            String requestBody = """
                    {
                        "id": %d,
                        "name": "%s",
                        "status": "%s",
                        "tags": [{"id": 0, "name": "%s"}]
                    }
                    """.formatted(randomId, name, currentStatus, tagName);

            given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .when()
                    .post("/pet")
                    .then()
                    .statusCode(200)
                    .log().ifValidationFails();

        }
    }

    /**
     * Töröljük a teszthez létrehozott 4 db állatot.
     */
    @AfterEach
    public void teardown() {
        // Ciklus az állatok törléséhez.
        for (Long id : createdPetIds) {
            given()
                    .when()
                    .delete("/pet/" + id)
                    .then()
                    .log().ifValidationFails()
                    .statusCode(anyOf(is(200), is(404)))
            // .body("$", anyOf(anEmptyMap(), nullValue()));
            ;
        }
    }
}