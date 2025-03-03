package com.example.staySphereProject.dto;

import java.util.ArrayList;

public class ListingResponse {

    private String listingId;
    private Double listingPricePerNight;
    private String listingTitle;
    private String listingDescription;
    private Integer guestLimit;
    private ArrayList<String> listingImages;



    public String getListingId() {
        return listingId;
    }

    public void setListingId(String listingId) {
        this.listingId = listingId;
    }


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
