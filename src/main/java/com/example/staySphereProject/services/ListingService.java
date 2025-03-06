package com.example.staySphereProject.services;

import com.example.staySphereProject.dto.ListingDTO;
import com.example.staySphereProject.dto.ListingResponse;
import com.example.staySphereProject.exeptions.ResourceNotFoundException;
import com.example.staySphereProject.models.Listing;
import com.example.staySphereProject.models.User;
import com.example.staySphereProject.repository.ListingRepository;
import com.example.staySphereProject.repository.UserRepository;
import com.example.staySphereProject.util.CheckAuthentication;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ListingService {
    private final ListingRepository listingRepository;
    private final UserRepository userRepository;
    private final CheckAuthentication checkAuthentication;
    //private final ReviewRepository reviewRepository;


    public ListingService(ListingRepository listingRepository, UserRepository userRepository, CheckAuthentication checkAuthentication) {
        this.listingRepository = listingRepository;
        this.userRepository = userRepository;
        this.checkAuthentication = checkAuthentication;
    }

    //Register listing
    public ListingResponse createListing(ListingDTO listingDTO ) {
        // Gets authentication method to check user before adding a new listing
        User user = checkAuthentication.validateAuthenticatedUser(listingDTO.getUserId());


        //Creating new listing
        Listing listing = new Listing();

        listing.setHost(user);
        listing.setListingTitle(listingDTO.getListingTitle());
        listing.setListingDescription(listingDTO.getListingDescription());
        listing.setListingPricePerNight(listingDTO.getListingPricePerNight());
        listing.setListingGuestLimit(listingDTO.getGuestLimit());
        listing.setListingImages(listingDTO.getListingImages());


        //standard values when creating an object
        listing.setListingActive(true);
        listing.setBooked(false);
        listing.setAvailable(new ArrayList<>());

        Listing savedListing = listingRepository.save(listing);

        return convertToDTO(savedListing);
    }


    public List<Listing> getAllListings(){

        return listingRepository.findAll();
    }


    //get listing by id
    public Optional<Listing> getListingById(String id) {
        return listingRepository.findById(id);
    }

    public Listing patchListing(Listing listing, String id) {
        Listing existingListing = listingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found"));
        User user = checkAuthentication.validateAuthenticatedUser(listingexistingListing.getHost());


        if (listing.getListingPricePerNight() > 0) {
            existingListing.setListingPricePerNight(listing.getListingPricePerNight());
        }
        if (listing.getListingTitle() != null) {
            existingListing.setListingTitle(listing.getListingTitle());
        }
        if (listing.getListingDescription() != null) {
            existingListing.setListingDescription(listing.getListingDescription());
        }
        if (listing.getAvailable() != null) {
            existingListing.setAvailable(listing.getAvailable());
        }
        if (listing.getListingImages() != null) {
            existingListing.setListingImages(listing.getListingImages());
        }
        if (listing.getListingGuestLimit() != null) {
            existingListing.setListingGuestLimit(listing.getListingGuestLimit());
        }
        if (listing.getHost() != null) {
            existingListing.setHost(listing.getHost());
        }

        return listingRepository.save(existingListing);
    }

    public void deleteListing(String id) {
        Listing existingListing = listingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found"));

        listingRepository.delete(existingListing);
    }



    //-------Hälp Mäthodz---------
    private ListingResponse convertToDTO(Listing listing) {
        ListingResponse listingResponse = new ListingResponse();

        listingResponse.setListingId(listing.getListingId());
        listingResponse.setListingTitle(listing.getListingTitle());
        listingResponse.setListingDescription(listing.getListingDescription());
        listingResponse.setGuestLimit(listing.getListingGuestLimit());
        listingResponse.setListingPricePerNight(listing.getListingPricePerNight());
        listingResponse.setListingDescription(listing.getListingDescription());
        listingResponse.setListingImages(listing.getListingImages());

        return listingResponse;
    }


}
