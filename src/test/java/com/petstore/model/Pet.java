package com.petstore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//POJO osztály (Plain Old Java Object)
//Csak azokat a mezőket veszi figyelembe, 
//amiket az osztály adattagjaiként létrehozunk.
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pet {

    private long id;
    private String name;
    private String status;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
