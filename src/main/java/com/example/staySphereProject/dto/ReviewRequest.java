package com.example.staySphereProject.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class ReviewRequest {

    private String userReviewer;

    private String reviewedListing;

    private String comment;

    private Integer reviewRating;



    //---------------GETTERS & SETTERS----------------
    public String getUserReviewer() {return userReviewer;}

    public void setUserReviewer(String userReviewer) {this.userReviewer = userReviewer;}

    public String getReviewedListing() {return reviewedListing;}

    public void setReviewedListing(String reviewedListing) {this.reviewedListing = reviewedListing;}

    public String getComment() {return comment;}

    public void setComment(String comment) {this.comment = comment;}

    public Integer getReviewRating() {return reviewRating;}

    public void setReviewRating(Integer reviewRating) {this.reviewRating = reviewRating;}

}
