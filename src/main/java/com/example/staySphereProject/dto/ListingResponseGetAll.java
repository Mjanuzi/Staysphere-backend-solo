package com.example.staySphereProject.dto;

import java.util.ArrayList;

public class ListingResponseGetAll {

    private String listingId;

    private String listingTitle;

    private String hostName;

    private Double listingPricePerNight;

    private ArrayList<String> listingImages;


    public String getListingId() {
        return listingId;
    }

    public void setListingId(String listingId) {
        this.listingId = listingId;
    }

    public String getListingTitle() {
        return listingTitle;
    }

    public void setListingTitle(String listingTitle) {
        this.listingTitle = listingTitle;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public Double getListingPricePerNight() {
        return listingPricePerNight;
    }

    public void setListingPricePerNight(Double listingPricePerNight) {
        this.listingPricePerNight = listingPricePerNight;
    }

    public ArrayList<String> getListingImages() {
        return listingImages;
    }

    public void setListingImages(ArrayList<String> listingImages) {
        this.listingImages = listingImages;
    }
}
