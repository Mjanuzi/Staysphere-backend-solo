package com.example.staySphereProject.dto;

import java.util.List;

public class ListingReviewedDTO {

    private String listingReviewed;

    private List<ReviewResponse> reviews;


    public ListingReviewedDTO() {

    }



    public String getListingReviewed() {
        return listingReviewed;
    }

    public void setListingReviewed(String listingReviewed) {
        this.listingReviewed = listingReviewed;
    }

    public List<ReviewResponse> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewResponse> reviews) {
        this.reviews = reviews;
    }


}
