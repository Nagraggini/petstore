package com.petstore.api.deletePet;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;

import com.petstore.api.base.BaseApiTest;

import static io.restassured.RestAssured.given;

public class DeletePetTest extends BaseApiTest {

    @Test
    void petDeletionTest() {

        long id = createdPetIds.get(0);
        String name = "Doggie_" + id;

        // Törlés.
        given().when().delete("/pet/" + id).then().log().ifValidationFails().statusCode(anyOf(is(200), is(204)));
    }

}
