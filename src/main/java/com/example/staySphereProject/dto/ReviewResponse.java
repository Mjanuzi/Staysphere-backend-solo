package com.example.staySphereProject.dto;

public class ReviewResponse {

    private String reviewerId;
    private String reviewerUsername;
    private String reviewedListing;
    private String reviewComment;
    private Integer reviewedRating;

    // Getters & Setters
    public String getReviewerId() { return reviewerId; }
    public void setReviewerId(String reviewerId) { this.reviewerId = reviewerId; }

    public String getReviewerUsername() { return reviewerUsername; }
    public void setReviewerUsername(String reviewerUsername) { this.reviewerUsername = reviewerUsername; }

    public String getReviewedListing() { return reviewedListing; }
    public void setReviewedListing(String reviewedListing) { this.reviewedListing = reviewedListing; }

    public String getReviewComment() { return reviewComment; }
    public void setReviewComment(String reviewComment) { this.reviewComment = reviewComment; }

    public Integer getReviewedRating() { return reviewedRating; }
    public void setReviewedRating(Integer reviewedRating) { this.reviewedRating = reviewedRating; }


}
