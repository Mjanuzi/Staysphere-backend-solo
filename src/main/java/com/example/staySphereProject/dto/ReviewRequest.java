package com.example.staySphereProject.dto;

import java.time.LocalDate;

public class ReviewRequest {
    private String userReviewer;

    private String reviewedListing;

    private String comment;

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
