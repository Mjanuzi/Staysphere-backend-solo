package com.example.staySphereProject.services;


import com.example.staySphereProject.dto.ListingDTO;
import com.example.staySphereProject.dto.ListingDTOConverter;
import com.example.staySphereProject.dto.ListingResponse;
import com.example.staySphereProject.models.Listing;
import com.example.staySphereProject.repository.ListingRepository;

public abstract class AbstractListingProcessor {

    protected final ListingRepository listingRepository;
    protected final ListingDTOConverter converter;

    protected AbstractListingProcessor(ListingRepository listingRepository, ListingDTOConverter converter) {
        this.listingRepository = listingRepository;
        this.converter = converter;
    }



// Template method for creating a new listing (residence, hotels.. etc )
    public final ListingResponse processListing(ListingDTO request) {

        //Step 1: validate request
        validateRequest(request);

        //step 2: Build the listing from the request
        Listing listing = buildListing(request);

        // Step 3: Apply business rules that us specific to the listing
        listing = applyBusinessRules(listing);

        //Step 4: Use the domain models template method for processing
        listing.processListing();

        // Step 5: Save the listing
        Listing savedListing = listingRepository.save(listing);

        // Step 6: Convert to response format
        return convertToResponse(savedListing);
    }
}
