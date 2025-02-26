package com.example.staySphereProject.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class ReviewRequest {
    @NotNull(message = "Review needs a valid User")
    private String userReviewer;

    @NotNull(message = "Review needs a valid listing")
    private String reviewedListing;

    @Max(value = 500,message = "Comment can not have more than 500 characters")
    private String comment;

    @Min(value = 1, message = "Minimum rating is 1")
    @Max(value = 5, message = "Maximum rating is 5")
    private Integer reviewRating;

    private boolean likedComment;

    private boolean dislikedComment;

    private LocalDate reviewDate;


    //---------------GETTERS & SETTERS----------------
    public String getUserReviewer() {return userReviewer;}

    public void setUserReviewer(String userReviewer) {this.userReviewer = userReviewer;}

    public String getReviewedListing() {return reviewedListing;}

    public void setReviewedListing(String reviewedListing) {this.reviewedListing = reviewedListing;}

    public String getComment() {return comment;}

    public void setComment(String comment) {this.comment = comment;}

    public Integer getReviewRating() {return reviewRating;}

    public void setReviewRating(Integer reviewRating) {this.reviewRating = reviewRating;}

    public boolean isLikedComment() {return likedComment;}

    public void setLikedComment(boolean likedComment) {this.likedComment = likedComment;}

    public boolean isDislikedComment() {return dislikedComment;}

    public void setDislikedComment(boolean dislikedComment) {this.dislikedComment = dislikedComment;}

    public LocalDate getReviewDate() {return reviewDate;}

    public void setReviewDate(LocalDate reviewDate) {this.reviewDate = reviewDate;}
}
