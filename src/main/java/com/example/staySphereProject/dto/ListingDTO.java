package com.example.staySphereProject.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
public class ListingDTO {

    @NotNull
    private String hostId;

    @NotNull
    private Double listingPricePerNight;

    @NotBlank
    private String listingTitle;

    @NotBlank
    private String listingDescription;

    @NotBlank
    private String location;

    @Min(value = 1)
    private Integer guestLimit;

    private Boolean listingActive;



    public Boolean getListingActive() {
        return listingActive;
    }

    public void setListingActive(Boolean listingActive) {
        this.listingActive = listingActive;
    }

    public @NotBlank String getLocation() {
        return location;
    }

    public void setLocation(@NotBlank String location) {
        this.location = location;
    }

    private ArrayList<String> listingImages;


    public String getHostId() { return hostId; }

    public void setHostId(String hostId) { this.hostId = hostId; }

    public Double getListingPricePerNight() {
        return listingPricePerNight;
    }

    public void setListingPricePerNight(Double listingPricePerNight) {
        this.listingPricePerNight = listingPricePerNight;
    }

    public String getListingTitle() {
        return listingTitle;
    }

    public void setListingTitle(String listingTitle) {
        this.listingTitle = listingTitle;
    }

    public String getListingDescription() {
        return listingDescription;
    }

    public void setListingDescription(String listingDescription) {
        this.listingDescription = listingDescription;
    }

    public Integer getGuestLimit() {
        return guestLimit;
    }

    public void setGuestLimit(Integer guestLimit) {
        this.guestLimit = guestLimit;
    }

    public ArrayList<String> getListingImages() {
        return listingImages;
    }

    public void setListingImages(ArrayList<String> listingImages) {
        this.listingImages = listingImages;
    }
}
