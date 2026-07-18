package com.petstore.api.putPet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.Test;

import com.petstore.api.base.BaseApiTest;

import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

public class PutPetTest extends BaseApiTest {

    private static final Logger LOG = LogManager.getLogger(PutPetTest.class);

    /**
     * TC4.1 - kisállat módosítása
     * előfeltétel: létező kisállat (ID xxx)
     * bemeneti adatok:
     * név: “Charm”
     * lépések:
     * Küldj PUT kérést a pet/xxx végpontra a megadott adatokkal
     * elvárt eredmény:
     * státuszkód: 200
     * a válasz tartalmaz id mezőt (1)
     * a válasz a módosított name adatot tartalmazza
     */
    @Test
    void petUpdateTest() {
        long id = createdPetIds.get(0);
        String name = "Charm";

        String requestBody = String.format("""
                               {
                  "id": %d,
                  "category": {
                    "id": 0,
                    "name": "test"
                  },
                  "name": "%s",
                  "photoUrls": [
                    "string"
                  ],
                  "tags": [
                    {
                      "id": 0,
                      "name": "string"
                    }
                  ],
                  "status": "available"
                }      """, id, name);

        given().contentType(ContentType.JSON).body(requestBody).when().put("/pet") // Van amikor zárójelben kell megadni
                                                                                   // az id-t is.
                .then().statusCode(200)
                .body("id", equalTo(id)).body("name", equalTo(name));
    }

}
