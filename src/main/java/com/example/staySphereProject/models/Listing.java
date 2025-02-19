package com.example.staySphereProject.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;


@Document(collection = "listings")
public class Listing {

    @Id
    private String listingId;

    @DBRef
    private Review review;

    @DBRef
    @NotBlank
    private User host;

    @NotBlank(message = "You need to give a title")
    private String listingTitle;

    @NotNull
    private double listingPricePerNight;

    @NotNull(message = "You Need to add atleast one guest")
    @Min(value = 1)
    private Integer listingGuestLimit;

    @Max(value = 2500, message = "The limit is 2500 characters")
    @NotBlank(message = "You cant leave this emppty")
    private String listingDescription;

    private ArrayList<String> listingImages;

    private boolean listingActive;

    private boolean isBooked;


    public Listing() {


    }


    public Listing(String listingId, User host, String listingTitle, double listingPricePerNight,
                   String listingDescription, Integer listingGuestLimit,
                   ArrayList<String> listingImages, boolean isBooked, boolean listingActive, Review review) {
        this.listingId = listingId;
        this.host = host;
        this.listingTitle = listingTitle;
        this.listingPricePerNight = listingPricePerNight;
        this.listingDescription = listingDescription;
        this.listingGuestLimit = listingGuestLimit;
        this.listingImages = listingImages;
        this.isBooked = isBooked;
        this.listingActive = listingActive;
        this.review = review;
    }
    public String getListingId() {
        return listingId;
    }
    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }
    public void setListingId(String listingId) {
        this.listingId = listingId;
    }

    public @NotBlank User getHost() {
        return host;
    }

    public void setHost(@NotBlank User host) {
        this.host = host;
    }

    public @NotBlank(message = "You need to give a title") String getListingTitle() {
        return listingTitle;
    }

    public void setListingTitle(@NotBlank(message = "You need to give a title") String listingTitle) {
        this.listingTitle = listingTitle;
    }

    @NotNull
    public double getListingPricePerNight() {
        return listingPricePerNight;
    }

    public void setListingPricePerNight(@NotNull double listingPricePerNight) {
        this.listingPricePerNight = listingPricePerNight;
    }

    public @NotNull(message = "You Need to add atleast one guest") @Min(value = 1) Integer getListingGuestLimit() {
        return listingGuestLimit;
    }

    public void setListingGuestLimit(@NotNull(message = "You Need to add atleast one guest") @Min(value = 1) Integer listingGuestLimit) {
        this.listingGuestLimit = listingGuestLimit;
    }

    public @Max(value = 2500, message = "The limit is 2500 characters") @NotBlank(message = "You cant leave this emppty") String getListingDescription() {
        return listingDescription;
    }

    public void setListingDescription(@Max(value = 2500, message = "The limit is 2500 characters") @NotBlank(message = "You cant leave this emppty") String listingDescription) {
        this.listingDescription = listingDescription;
    }

    public ArrayList<String> getListingImages() {
        return listingImages;
    }

    public void setListingImages(ArrayList<String> listingImages) {
        this.listingImages = listingImages;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public boolean isListingActive() {
        return listingActive;
    }

    public void setListingActive(boolean listingActive) {
        this.listingActive = listingActive;
    }


}
