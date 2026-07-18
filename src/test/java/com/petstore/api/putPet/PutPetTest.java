package com.petstore.api.putPet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.Test;

import com.petstore.api.base.BaseApiTest;
import com.petstore.model.Pet;

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

  @Test
  void petUpdateTestWithLog() {
    LOG.info("TC4.1 indítása, kisállat módosítása");

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

    LOG.info("Módosítandó kisállat:id ", id);

    try {
      given().contentType(ContentType.JSON).body(requestBody).when().put("/pet") // Van amikor zárójelben kell megadni
          // az id-t is.
          .then().statusCode(200)
          .body("id", equalTo(id)).body("name", equalTo(name));

      LOG.info("TC4.1 sikeresen lefutott");
    } catch (Exception e) {
      LOG.info("Hiba történt a teszt futtatása közben: ", e);
      throw e;
    }
  }

  @Test
  void petUpdateTestWithPOJO() {
    long petId = createdPetIds.get(1);
    String name = "Paddington";

    Pet pet = new Pet();

    pet.setId(petId);
    pet.setName(name);
    pet.setStatus("available");

    given().accept(ContentType.JSON).contentType(ContentType.JSON).body(pet) // Elküldjük a pet objektumot.
        .when().put("pet/").then().log()
        .ifValidationFails().statusCode(200).body("id", equalTo(petId))
        .body("name", equalTo("Paddington"))
        .body("status", equalTo("available"));
  }

}
