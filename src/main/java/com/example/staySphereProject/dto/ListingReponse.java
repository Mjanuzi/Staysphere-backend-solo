package com.example.staySphereProject.dto;

import java.util.ArrayList;

public class ListingReponse {
    private String listingOwner;
    private double pricePerNight;
    private String listingTitle;
    private String listingDescription;
    private Integer guestLimit;
    private ArrayList<String> listingImages;


    public String getListingOwner() {
        return listingOwner;
    }

    public void setListingOwner(String listingOwner) {
        this.listingOwner = listingOwner;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
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
