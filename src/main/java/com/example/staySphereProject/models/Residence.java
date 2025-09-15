package com.example.staySphereProject.models;


import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Residence")
public class Residence extends Listing {

    @DBRef
    private User host;


    public Residence() {
        super();

    }

}
