package com.example.staySphereProject.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class ReviewRequest {


    @NotNull(message = "Listing can not be null for a review")
    @NotEmpty(message = "Listing can not be empty for a review")
    private String reviewedListing;

    @NotNull(message = "Comment can null for a review")
    @NotEmpty(message = "Comment can not be empty for review")
    private String comment;

    @Min(value = 0,message = "Rating must be minimum 0 for a review")
    @Max(value = 5, message = "Rating must be maximum 5 for a review")
    private Integer reviewRating;


//-------------GETTER AND SETTERS-------------------
    public @NotNull(message = "Listing can not be null for a review") @NotEmpty(message = "Listing can not be empty for a review") String getReviewedListing() {
        return reviewedListing;
    }

    public void setReviewedListing(@NotNull(message = "Listing can not be null for a review") @NotEmpty(message = "Listing can not be empty for a review") String reviewedListing) {
        this.reviewedListing = reviewedListing;
    }

    public @NotNull(message = "Comment can null for a review") @NotEmpty(message = "Comment can not be empty for review") String getComment() {
        return comment;
    }

    public void setComment(@NotNull(message = "Comment can null for a review") @NotEmpty(message = "Comment can not be empty for review") String comment) {
        this.comment = comment;
    }

    public @Min(value = 0, message = "Rating must be minimum 0 for a review") @Max(value = 5, message = "Rating must be maximum 5 for a review") Integer getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(@Min(value = 0, message = "Rating must be minimum 0 for a review") @Max(value = 5, message = "Rating must be maximum 5 for a review") Integer reviewRating) {
        this.reviewRating = reviewRating;
    }
}
