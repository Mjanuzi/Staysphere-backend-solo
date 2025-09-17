package com.example.staySphereProject.converters;

import com.example.staySphereProject.dto.ListingDTO;
import com.example.staySphereProject.dto.ListingResponse;
import com.example.staySphereProject.dto.ListingResponseGetAll;
import com.example.staySphereProject.models.Listing;
import com.example.staySphereProject.models.Residence;
import com.example.staySphereProject.models.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ListingDTOConverter {

    public Listing fromDTO(ListingDTO dto, User host) {
        Residence listing = new Residence();

        listing.setHost(host);
        listing.setListingTitle(dto.getListingTitle());
        listing.setListingDescription(dto.getListingDescription());
        listing.setListingPricePerNight(dto.getListingPricePerNight());
        listing.setListingGuestLimit(dto.getGuestLimit());
        listing.setListingImages(dto.getListingImages());
        listing.setLocation(dto.getLocation());

        //standard values when creating an object
        listing.setListingActive(true);
        listing.setAvailable(new ArrayList<>());
        listing.setReview(new ArrayList<>());
        listing.setBooked(false);

        return listing;
    }

    public ListingResponse toResponse(Listing listing) {
        ListingResponse response = new ListingResponse();

        response.setListingId(listing.getListingId());
        response.setListingTitle(listing.getListingTitle());
        response.setListingDescription(listing.getListingDescription());
        response.setGuestLimit(listing.getListingGuestLimit());
        response.setListingPricePerNight(listing.getListingPricePerNight());
        response.setListingImages(listing.getListingImages());
        response.setAvailable(listing.getAvailable());
        response.setLocation(listing.getLocation());
        response.setListingActive(listing.isListingActive());

        // Set host information if available
        if (hasHost(listing)) {
            response.setHostId(getHostId(listing));
            response.setHostName(getHostName(listing));
        }

        return response;
    }

    public ListingResponseGetAll toGetAllResponse(Listing listing) {
        ListingResponseGetAll response = new ListingResponseGetAll();

        response.setListingId(listing.getListingId());
        response.setListingTitle(listing.getListingTitle());
        response.setListingPricePerNight(listing.getListingPricePerNight());
        response.setListingImages(listing.getListingImages());
        response.setLocation(listing.getLocation());
        response.setListingActive(listing.isListingActive());

        // Set host name if available
        if (hasHost(listing)) {
            response.setHostName(getHostName(listing));
        }

        return response;
    }

    public Listing applyUpdate(Listing existing, ListingDTO dto) {
        if (dto.getListingTitle() != null) {
            existing.setListingTitle(dto.getListingTitle());
        }
        if (dto.getListingDescription() != null) {
            existing.setListingDescription(dto.getListingDescription());
        }
        if (dto.getListingPricePerNight() != null) {
            existing.setListingPricePerNight(dto.getListingPricePerNight());
        }
        if (dto.getGuestLimit() != null) {
            existing.setListingGuestLimit(dto.getGuestLimit());
        }
        if (dto.getListingImages() != null) {
            existing.setListingImages(dto.getListingImages());
        }
        if (dto.getLocation() != null) {
            existing.setLocation(dto.getLocation());
        }
        if (dto.getListingActive() != null) {
            existing.setListingActive(dto.getListingActive());
        }

        return existing;
    }

    private boolean hasHost(Listing listing) {
        // Check if this is a Residence (which has a host)
        return listing instanceof Residence;
    }

    private String getHostId(Listing listing) {
        if (listing instanceof Residence) {
            Residence residence = (Residence) listing;
            return residence.getHost() != null ? residence.getHost().getId() : null;
        }
        // For future Hotel implementations, return hotel-specific ID
        return null;
    }

    private String getHostName(Listing listing) {
        if (listing instanceof Residence) {
            Residence residence = (Residence) listing;
            return residence.getHost() != null ? residence.getHost().getUsername() : null;
        }
        // For future Hotel implementations, return hotel-specific name
        return null;
    }
}
