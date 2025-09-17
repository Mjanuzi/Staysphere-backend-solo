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
    private String id;

    @DBRef
    private ArrayList<Review> review;

    @NotBlank(message = "You need to give a title")
    private String listingTitle;

    @NotNull
    private Double listingPricePerNight;

    @NotNull(message = "You Need to add atleast one guest")
    @Min(value = 1)
    private Integer listingGuestLimit;

    @NotBlank(message = "You cant leave this emppty")
    private String listingDescription;

    private ArrayList<String> listingImages;

    @NotNull
    private ArrayList<LocalDate> available ;

    @NotNull
    private boolean listingActive;

    @NotNull
    private String location;

    @NotNull
    private boolean isBooked;


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




    //Template Method Pattern


    public final void validateReadyForPublish(){
        validateCommonFields();
        validateListingType();
        validateBusinessRules();
    }


    protected void validateCommonFields(){
        if(listingTitle == null || listingTitle.trim().isEmpty()){
            throw new IllegalArgumentException("You must provide a title for the listing");
        }
        if (listingPricePerNight == null || listingPricePerNight < 0){
            throw new IllegalArgumentException("You must provide a positive number for the listing price per night");
        }
        if (listingGuestLimit == null || listingGuestLimit < 1){
            throw new IllegalArgumentException("There must be at least one guest");
        }
        if (listingDescription == null || listingDescription.trim().isEmpty()){
            throw new IllegalArgumentException("You must provide a proper description for the listing");
        }
        if (location == null || location.trim().isEmpty()){
            throw new IllegalArgumentException("You must provide a proper location for the listing");
        }
    }

    protected void validateBusinessRules(){
        // Common business rules for all listing types
        if (listingPricePerNight > 10000.0) {
            throw new IllegalArgumentException("Listing price cannot exceed 10,000 per night");
        }
        if (listingGuestLimit > 50) {
            throw new IllegalArgumentException("Guest limit cannot exceed 50 people");
        }
    }

    protected abstract void validateListingType();



}