package com.example.staySphereProject.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;

public class ListingDTO {
    @NotBlank
    private String listingOwner;
    @NotNull
    private double pricePerNight;
    @NotBlank
    private String listingTitle;
    @NotBlank
    private String listingDescription;
    @Min(value = 1)
    private Integer guestLimit;
    //@NotBlank
    private ArrayList<String> listingImages;


    public @NotBlank String getListingOwner() {
        return listingOwner;
    }

    public void setListingOwner(@NotBlank String listingOwner) {
        this.listingOwner = listingOwner;
    }

    @NotNull
    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(@NotNull double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public @NotBlank String getListingTitle() {
        return listingTitle;
    }

    public void setListingTitle(@NotBlank String listingTitle) {
        this.listingTitle = listingTitle;
    }

    public @NotBlank String getListingDescription() {
        return listingDescription;
    }

    public void setListingDescription(@NotBlank String listingDescription) {
        this.listingDescription = listingDescription;
    }

    public @Min(value = 1) Integer getGuestLimit() {
        return guestLimit;
    }

    public void setGuestLimit(@Min(value = 1) Integer guestLimit) {
        this.guestLimit = guestLimit;
    }

    public ArrayList<String> getListingImages() {
        return listingImages;
    }

    public void setListingImages(ArrayList<String> listingImages) {
        this.listingImages = listingImages;
    }
}
