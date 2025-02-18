package com.example.staySphereProject.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
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

    @DBRef
    @NotNull(message = "User can not be null in a review")
    @NotEmpty(message = "User can not be empty in a review")
    private User userReviewer;

    @DBRef
    @NotNull(message = "Listing can not be null in a review")
    @NotEmpty(message = "Listing can not be empty in a review ")
    private Listing listingReviewed;

    @Max(value = 500, message = "Review can not have more than 500 characters")
    private String comment;

    @Min(value = 1)
    @Max(value = 5)
    private Integer rating;

    private boolean LikeComment;

    private boolean DislikeComment;

    private LocalDateTime reviewDateSet;


}
