package com.example.staySphereProject.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "reviews")
public class Review {

    @Id
    private String id;

    private User user;

    private Listing listing;

    private String comment;

    private Integer rating;

    private boolean LikeComment;

    private boolean DislikeComment;

    private LocalDateTime reviewDateSet;


}
