package com.petstore.api.petList;

import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.Test;

import com.petstore.api.base.BaseApiTest;

import static io.restassured.RestAssured.given;

public class PetListTest extends BaseApiTest {

    /**
     * TC1.1 Elérhető állatok listájának lekérése
     * előfeltétel: json formátumú választ várunk
     * lépések:
     * Küldj GET kérést a /pet végpontra.
     * elvárt eredmény:
     * http státuszkód: 200
     * a válasz egy lista
     * a lista nem üres
     * a lista elemei tartalmaznak “id” és “name” mezőket
     */
    @Test
    void petListTest() {
        String statusLvl = "available";

        given().when().get("/pet/findByStatus?status={statusLvl}", statusLvl).then().statusCode(200).body("$",
                notNullValue())// a válasz nem nullérték
                .body("$", instanceOf(List.class)) // a válasz egy lista
                .body("size()", greaterThan(0)) // a lista nem üres
                .body("[0].id", notNullValue()) // az első elemnek van id mezője
                .body("[0].name", notNullValue()); // az első elemnek van name mezője
    }

}
