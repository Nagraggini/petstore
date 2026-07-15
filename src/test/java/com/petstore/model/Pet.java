package com.petstore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//POJO osztály
//Csak azokat a mezőket veszi figyelembe, 
//amiket az osztály adattagjaiként létrehozunk.
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pet {
    private int id;
    private String name;
    private String status;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }
}
