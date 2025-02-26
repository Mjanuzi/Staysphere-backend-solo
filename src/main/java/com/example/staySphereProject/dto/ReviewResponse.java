package com.example.staySphereProject.dto;

import java.time.LocalDateTime;

public class ReviewResponse {

    private String reviewerName;
    private String reviewedListing;
    private String reviewComment;
    private String reviewedRating;
    private boolean likedComment;
    private boolean dislikedComment;
    private LocalDateTime reviewDate;


    //------------------GETTER & SETTER--------------------
    public String getReviewerName() {return reviewerName;}

    public void setReviewerName(String reviewerName) {this.reviewerName = reviewerName;}

    public String getReviewedListing() {return reviewedListing;}

    public void setReviewedListing(String reviewedListing) {this.reviewedListing = reviewedListing;}

    public String getReviewComment() {return reviewComment;}

    public void setReviewComment(String reviewComment) {this.reviewComment = reviewComment;}

    public String getReviewedRating() {return reviewedRating;}

    public void setReviewedRating(String reviewedRating) {this.reviewedRating = reviewedRating;}

    public boolean isLikedComment() {return likedComment;}

    public void setLikedComment(boolean likedComment) {this.likedComment = likedComment;}

    public boolean isDislikedComment() {return dislikedComment;}

    public void setDislikedComment(boolean dislikedComment) {this.dislikedComment = dislikedComment;}

    public LocalDateTime getReviewDate() {return reviewDate;}

    public void setReviewDate(LocalDateTime reviewDate) {this.reviewDate = reviewDate;}
}
