package com.example.staySphereProject.dto;

public class ReviewResponse {

    private String ReviewerId;
    private String reviewerUsername;
    private String reviewedListing;
    private String reviewComment;
    private String reviewedRating;



    //------------------GETTER & SETTER--------------------
    public String getReviewerUsername() {return reviewerUsername;}

    public void setReviewerUsername(String reviewerUsername) {this.reviewerUsername = reviewerUsername;}

    public String getReviewedListing() {return reviewedListing;}

    public void setReviewedListing(String reviewedListing) {this.reviewedListing = reviewedListing;}

    public String getReviewComment() {return reviewComment;}

    public void setReviewComment(String reviewComment) {this.reviewComment = reviewComment;}

    public String getReviewedRating() {return reviewedRating;}

    public void setReviewedRating(String reviewedRating) {this.reviewedRating = reviewedRating;}



}
