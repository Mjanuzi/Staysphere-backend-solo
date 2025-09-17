package com.example.staySphereProject.services;


import com.example.staySphereProject.converters.ListingDTOConverter;
import com.example.staySphereProject.dto.ListingDTO;
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

    //Update a listing
    public final ListingResponse updateListing(String listingId, ListingDTO request) {
        // Step 1: Validate the request
        validateRequest(request);

        // Step 2: Find and validate existing listing
        Listing existingListing = findExistingListing(listingId);

        // Step 3: Apply updates
        Listing updatedListing = applyUpdates(existingListing, request);

        // Step 4: Apply business rules
        updatedListing = applyBusinessRules(updatedListing);

        // Step 5: Use domain model validation
        updatedListing.validateReadyForPublish();

        // Step 6: Save and return
        Listing savedListing = listingRepository.save(updatedListing);
        return convertToResponse(savedListing);
    }

    // Extension pojnts - our subclasses must implement these (residence in our case for now)

    //Validates the request before processing
    protected abstract void validateRequest(ListingDTO request);

    //Building the listing from request
    protected abstract Listing buildListing(ListingDTO request);

    //Applying business rules specific
    protected abstract Listing applyBusinessRules(Listing listing);

    //Converts the saved listing to a response format
    protected abstract ListingResponse convertToResponse(Listing listing);


    //finds the existing listing by an ID
    protected Listing findExistingListing(String listingId) {
        return listingRepository.findById(listingId)
                .orElseThrow(()-> new RuntimeException("Listing with id " + listingId + " not found"));
    }

    //applying updates from dto to existing listing
    protected Listing applyUpdates(Listing existingListing, ListingDTO request) {
        return converter.applyUpdate(existingListing, request);
    }


    ////common validation for all types of listings
    protected void validateCommonRequest(ListingDTO request) {
        if (request == null) {
            throw new IllegalArgumentException("Listing request cannot be null");
        }
        if (request.getHostId() == null || request.getHostId().trim().isEmpty()) {
            throw new IllegalArgumentException("Host id cannot be null or empty");
        }
    }

    protected void applyCommonBusinessRules(Listing listing) {

        //Applying common business rules for all types of listings here

    }
}
