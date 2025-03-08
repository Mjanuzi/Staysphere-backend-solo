package com.example.staySphereProject.models;

import jakarta.validation.constraints.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;


@Document(collection = "listings")
public class Listing {


    @Id
    private String listingId;

    @DBRef
    private ArrayList<Review> review;

    @DBRef
    private User host;

    @NotBlank(message = "You need to give a title")
    private String listingTitle;

    @NotNull
    private Double listingPricePerNight;

    @NotNull(message = "You Need to add atleast one guest")
    @Min(value = 1)
    private Integer listingGuestLimit;

    //@Max(value = 2500, message = "The limit is 2500 characters")

    @NotBlank(message = "You cant leave this emppty")
    private String listingDescription;

    //@NotNull
    private ArrayList<String> listingImages;

    @NotNull
    private ArrayList<LocalDate> available ;

    @NotNull
    private boolean listingActive;


    @NotNull
    private boolean isBooked;


    /*public Listing(String listingId, User host, String listingTitle, Double listingPricePerNight,
                   String listingDescription, Integer listingGuestLimit,
                   ArrayList<String> listingImages, boolean isBooked, boolean listingActive, Review review, ArrayList<LocalDate> available) {
        this.listingId = listingId;
        this.host = host;
        this.listingTitle = listingTitle;
        this.listingPricePerNight = listingPricePerNight;
        this.listingDescription = listingDescription;
        this.listingGuestLimit = listingGuestLimit;
        this.listingImages = listingImages;
        this.isBooked = isBooked;
        this.listingActive = listingActive;
        //this.review = review;
        this.available = available;
    }*/

    public Listing() {
    }





    /*public Review getReview() {
        return review;
    }*/
    public ArrayList<LocalDate> getAvailable() {
        return available;
    }

    public void setAvailable(ArrayList<LocalDate> available) {
        this.available = available;
    }
    /*public void setReview(Review review) {
        this.review = review;
    }*/
    public @NotEmpty String getListingId() {
        return listingId;
    }

    public ArrayList<Review> getReview() {
        return review;
    }

    public void setReview(ArrayList<Review> review) {
        this.review = review;
    }

    public void setListingId(@NotEmpty String listingId) {

        this.listingId = listingId;
    }

    public User getHost() {
        return host;
    }

    public void setHost( User host) {
        this.host = host;
    }

    public @NotBlank(message = "You need to give a title") String getListingTitle() {
        return listingTitle;
    }

    public void setListingTitle(@NotBlank(message = "You need to give a title") String listingTitle) {
        this.listingTitle = listingTitle;
    }

    @NotNull
    public Double getListingPricePerNight() {
        return listingPricePerNight;
    }

    public void setListingPricePerNight(@NotNull Double listingPricePerNight) {
        this.listingPricePerNight = listingPricePerNight;
    }

    public @NotNull(message = "You Need to add atleast one guest") @Min(value = 1) Integer getListingGuestLimit() {
        return listingGuestLimit;
    }

    public void setListingGuestLimit(@NotNull(message = "You Need to add atleast one guest") @Min(value = 1) Integer listingGuestLimit) {
        this.listingGuestLimit = listingGuestLimit;
    }

    public /*@Max(value = 2500, message = "The limit is 2500 characters")*/ @NotBlank(message = "You cant leave this emppty") String getListingDescription() {
        return listingDescription;
    }

    public void setListingDescription(/*@Max(value = 2500, message = "The limit is 2500 characters")*/ @NotBlank(message = "You cant leave this emppty") String listingDescription) {
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
