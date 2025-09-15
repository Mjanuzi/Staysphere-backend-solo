package com.example.staySphereProject.models;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.util.ArrayList;

@Document(collection = "listings")
public abstract class Listing {

    @Id
    protected String id;

    @DBRef
    protected ArrayList<Review> review;

    //@DBRef
    //private User host;

    @NotBlank(message = "You need to give a title")
    protected String listingTitle;

    @NotNull
    protected Double listingPricePerNight;

    @NotNull(message = "You Need to add atleast one guest")
    @Min(value = 1)
    protected Integer listingGuestLimit;

    @NotBlank(message = "You cant leave this emppty")
    protected String listingDescription;

    protected ArrayList<String> listingImages;

    @NotNull
    protected ArrayList<LocalDate> available ;

    @NotNull
    protected boolean listingActive;

    @NotNull
    protected String location;

    @NotNull
    protected boolean isBooked;





    public Listing() {
    }




    public @NotNull String getLocation() {
        return location;
    }

    public void setLocation(@NotNull String location) {
        this.location = location;
    }

    public ArrayList<LocalDate> getAvailable() {
        return available;
    }

    public void setAvailable(ArrayList<LocalDate> available) {
        this.available = available;
    }

    public @NotEmpty String getListingId() {
        return id;
    }

    public ArrayList<Review> getReview() {
        return review;
    }

    public void setReview(ArrayList<Review> review) {
        this.review = review;
    }

    public void setListingId(@NotEmpty String listingId) {

        this.id = listingId;
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

    public @NotBlank(message = "You cant leave this emppty") String getListingDescription() {
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