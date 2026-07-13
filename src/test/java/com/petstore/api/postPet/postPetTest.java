package com.petstore.api.postPet;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.petstore.api.base.BaseApiTest;

public class postPetTest extends BaseApiTest {

    @Test
    void testPost() {
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("name", "Cute");
        // status
        map.put("status", "available");

        System.out.println(map);

    }
}
