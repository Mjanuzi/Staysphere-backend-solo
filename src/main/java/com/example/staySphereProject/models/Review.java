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
    private Integer reviewRating;

    private boolean likeComment;

    private boolean dislikeComment;

    private LocalDateTime reviewDateSet;

    //------------CONSTRUCTORS---------------
    public Review() {
    }

    public Review(String id, User userReviewer, Listing listingReviewed, String comment, Integer reviewRating, boolean likeComment, boolean dislikeComment, LocalDateTime reviewDateSet) {
        this.id = id;
        this.userReviewer = userReviewer;
        this.listingReviewed = listingReviewed;
        this.comment = comment;
        this.reviewRating = reviewRating;
        this.likeComment = likeComment;
        this.dislikeComment = dislikeComment;
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

    public @NotNull(message = "Listing can not be null in a review") @NotBlank(message = "Listing can not be empty in a review ") Listing getListingReviewed() {
        return listingReviewed;
    }

    public void setListingReviewed(@NotNull(message = "Listing can not be null in a review") @NotBlank(message = "Listing can not be empty in a review ") Listing listingReviewed) {
        this.listingReviewed = listingReviewed;
    }

    public @Max(value = 500, message = "Review can not have more than 500 characters") @NotBlank(message = "Comment can not be blank") String getComment() {
        return comment;
    }

    public void setComment(@Max(value = 500, message = "Review can not have more than 500 characters") @NotBlank(message = "Comment can not be blank") String comment) {
        this.comment = comment;
    }

    public @Min(value = 1) @Max(value = 5) Integer getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(@Min(value = 1) @Max(value = 5) Integer reviewRating) {
        this.reviewRating = reviewRating;
    }

    public boolean isLikeComment() {
        return likeComment;
    }

    public void setLikeComment(boolean likeComment) {
        this.likeComment = likeComment;
    }

    public boolean isDislikeComment() {
        return dislikeComment;
    }

    public void setDislikeComment(boolean dislikeComment) {
        this.dislikeComment = dislikeComment;
    }

    public LocalDateTime getReviewDateSet() {
        return reviewDateSet;
    }

    public void setReviewDateSet(LocalDateTime reviewDateSet) {
        this.reviewDateSet = reviewDateSet;
    }
    //--------------GETTERS AND SETTERS--------------







}
