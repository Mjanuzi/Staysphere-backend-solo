package com.example.staySphereProject.models;

import jakarta.validation.constraints.*;
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
    @NotBlank(message = "User can not be empty in a review")
    private User userReviewer;

    @DBRef
    @NotNull(message = "Listing can not be null in a review")
    @NotBlank(message = "Listing can not be empty in a review ")
    private Listing listingReviewed;

    @Max(value = 500, message = "Review can not have more than 500 characters")
    @NotBlank(message = "Comment can not be blank")
    private String comment;

    @Min(value = 1)
    @Max(value = 5)
    private Integer rating;

    private boolean LikeComment;

    private boolean DislikeComment;

    private LocalDateTime reviewDateSet;


    public Review() {
    }

    public Review(String id, User userReviewer, Listing listingReviewed, String comment, Integer rating, boolean likeComment, boolean dislikeComment, LocalDateTime reviewDateSet) {
        this.id = id;
        this.userReviewer = userReviewer;
        this.listingReviewed = listingReviewed;
        this.comment = comment;
        this.rating = rating;
        LikeComment = likeComment;
        DislikeComment = dislikeComment;
        this.reviewDateSet = reviewDateSet;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public @NotNull(message = "User can not be null in a review") @NotBlank(message = "User can not be empty in a review") User getUserReviewer() {
        return userReviewer;
    }

    public void setUserReviewer(@NotNull(message = "User can not be null in a review") @NotBlank(message = "User can not be empty in a review") User userReviewer) {
        this.userReviewer = userReviewer;
    }

    public Listing getListingReviewed() {
        return listingReviewed;
    }

    public void setListingReviewed(Listing listingReviewed) {
        this.listingReviewed = listingReviewed;
    }

    public @Max(value = 500, message = "Review can not have more than 500 characters") @NotBlank(message = "Comment can not be blank") String getComment() {
        return comment;
    }

    public void setComment(@Max(value = 500, message = "Review can not have more than 500 characters") @NotBlank(message = "Comment can not be blank") String comment) {
        this.comment = comment;
    }

    public @Min(value = 1) @Max(value = 5) Integer getRating() {
        return rating;
    }

    public void setRating(@Min(value = 1) @Max(value = 5) Integer rating) {
        this.rating = rating;
    }

    public boolean isLikeComment() {
        return LikeComment;
    }

    public void setLikeComment(boolean likeComment) {
        LikeComment = likeComment;
    }

    public boolean isDislikeComment() {
        return DislikeComment;
    }

    public void setDislikeComment(boolean dislikeComment) {
        DislikeComment = dislikeComment;
    }

    public LocalDateTime getReviewDateSet() {
        return reviewDateSet;
    }

    public void setReviewDateSet(LocalDateTime reviewDateSet) {
        this.reviewDateSet = reviewDateSet;
    }
}
