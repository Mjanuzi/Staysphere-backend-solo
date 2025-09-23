package com.example.staySphereProject.services;

import com.example.staySphereProject.converters.ListingDTOConverter;
import com.example.staySphereProject.dto.ListingDTO;
import com.example.staySphereProject.dto.ListingResponse;
import com.example.staySphereProject.models.Listing;
import com.example.staySphereProject.models.Residence;
import com.example.staySphereProject.models.User;
import com.example.staySphereProject.repository.ListingRepository;
import com.example.staySphereProject.repository.UserRepository;
import com.example.staySphereProject.util.CheckAuthentication;
import org.springframework.stereotype.Service;

@Service
public class ResidenceProcessor extends AbstractListingProcessor {

    private final UserRepository userRepository;
    private final CheckAuthentication checkAuthentication;

    public ResidenceProcessor(ListingRepository listingRepository,
                              ListingDTOConverter converter,
                              UserRepository userRepository,
                              CheckAuthentication checkAuthentication) {
        super(listingRepository, converter);
        this.userRepository = userRepository;
        this.checkAuthentication = checkAuthentication;
    }

    @Override
    protected void validateRequest(ListingDTO request) {
        validateCommonRequest(request);

        if (request.getGuestLimit() != null && request.getGuestLimit() > 20) {
            throw new IllegalArgumentException("Can't have more than 20 guests");
        }

        if (request.getListingPricePerNight() != null && request.getListingPricePerNight() < 10) {
            throw new IllegalArgumentException("Can't charge less than 10 per night");
        }

        if (request.getListingTitle() != null && request.getListingTitle().toLowerCase().contains("hotel")) {
            throw new IllegalArgumentException("Can't host a hotel as a user");
        }
    }

    @Override
    protected Listing buildListing(ListingDTO request) {

        User host = checkAuthentication.validateAuthenticatedUser(request.getHostId());

        Listing listing = converter.fromDTO(request, host);

        // Ensure we have a residence instance
        if (!(listing instanceof Residence)) {
            throw new IllegalStateException("Converter should ONLY create Residence listings");
        }

        return listing;
    }

    @Override
    protected Listing applyBusinessRules(Listing listing) {

        // common business rules first
        applyCommonBusinessRules(listing);

        //specific business rules second
        Residence residence = (Residence) listing;

        //Verify user has
        User host = residence.getHost();
        if (host.getUsername() == null || host.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("User must have a valid name");
        }

        return residence;



    }
    @Override
    protected ListingResponse convertToResponse(Listing listing) {

        ListingResponse response = converter.toResponse(listing);


        //We can apply residence-specific responses here

        return response;
    }
}


