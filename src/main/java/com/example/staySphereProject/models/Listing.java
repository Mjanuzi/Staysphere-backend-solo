package com.example.staySphereProject.models;

import jdk.jfr.Description;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;


@Document(collection = "listing")
public class Listing {

    @Id
    private String listingId;

    private String listingTitle;

    private String listingDescription;

    private ArrayList<String> listingImages;

    private boolean listingActive;

    private boolean isbooked;

    public Listing(String listingId, String listingTitle, String listingDescription, ArrayList<String> listingImages, boolean listingActive, boolean isbooked) {
        this.listingId = listingId;
        this.listingTitle = listingTitle;
        this.listingDescription = listingDescription;
        this.listingImages = listingImages;
        this.listingActive = listingActive;
        this.isbooked = isbooked;
    }

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

    public String getListingDescription() {
        return listingDescription;
    }

    public void setListingDescription(String listingDescription) {
        this.listingDescription = listingDescription;
    }

    public ArrayList<String> getListingImages() {
        return listingImages;
    }

    public void setListingImages(ArrayList<String> listingImages) {
        this.listingImages = listingImages;
    }

    public boolean isListingActive() {
        return listingActive;
    }

    public void setListingActive(boolean listingActive) {
        this.listingActive = listingActive;
    }

    public boolean isIsbooked() {
        return isbooked;
    }

    public void setIsbooked(boolean isbooked) {
        this.isbooked = isbooked;
    }

}
