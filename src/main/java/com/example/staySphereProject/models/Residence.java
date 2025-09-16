package com.example.staySphereProject.models;


import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "listings")
public class Residence extends Listing {



    @DBRef
    @NotNull(message = "Residence must have an host")
    private User host;

    public @NotNull(message = "Residence must have an host") User getHost() {
        return host;
    }

    public void setHost(@NotNull(message = "Residence must have an host") User host) {
        this.host = host;
    }
}
