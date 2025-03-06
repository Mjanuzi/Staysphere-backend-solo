package com.example.staySphereProject.services;

import com.example.staySphereProject.dto.ListingDTO;
import com.example.staySphereProject.dto.ListingResponse;
import com.example.staySphereProject.exeptions.ResourceNotFoundException;
import com.example.staySphereProject.models.Listing;
import com.example.staySphereProject.models.User;
import com.example.staySphereProject.repository.ListingRepository;
import com.example.staySphereProject.repository.UserRepository;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListingService {
    private final ListingRepository listingRepository;
    private final UserRepository userRepository;
    //private final ReviewRepository reviewRepository;


    public ListingService(ListingRepository listingRepository, UserRepository userRepository) {
        this.listingRepository = listingRepository;
        this.userRepository = userRepository;
    }

    /*@Transactional
    public Listing addAvailability(String listingId, AvailabilityRequest request, String hostId) {
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found"));

        // Verify host ownership
        if (!listing.getHostId().equals(hostId)) {
            throw new AccessDeniedException("You don't own this listing");
        }

        // Validate date range
        validateDateRange(request.getStartDate(), request.getEndDate());

        // Generate dates
        List<LocalDate> datesToAdd = generateDateRange(request.getStartDate(), request.getEndDate());*/

    //Register listing
    public ListingResponse createListing(ListingDTO listingDTO) {
            User host = userRepository.findById(listingDTO.getHostId())
                .orElseThrow(() -> new ResourceNotFoundException("Host not found"));


            //Creating new listing
            Listing listing = new Listing();
            listing.setHost(host);
            listing.setListingTitle(listingDTO.getListingTitle());
            listing.setListingDescription(listingDTO.getListingDescription());
            listing.setListingPricePerNight(listingDTO.getListingPricePerNight());
            listing.setListingGuestLimit(listingDTO.getGuestLimit());
            listing.setListingImages(listingDTO.getListingImages());


            //standard values when creating an object
            listing.setListingActive(true);
            listing.setAvailable(new ArrayList<>());

            Listing savedListing = listingRepository.save(listing);
            return convertToDTO(savedListing);

    }


    public List<ListingResponse> getAllListings () {

        return listingRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    //get listing by id
    public ListingResponse getListingById (String listingId){
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found"));
        return convertToDTO(listing);
    }

    public ListingResponse patchListing (String listingId, ListingDTO listingDTO){
        Listing existingListing = listingRepository.findById(listingId)
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found"));

        if (listingDTO.getHostId() != null) {
            User newHost = userRepository.findById(listingDTO.getHostId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            existingListing.setHost(newHost);
        }
        if (listingDTO.getListingTitle() != null) {
            existingListing.setListingTitle(listingDTO.getListingTitle());
        }
        if (listingDTO.getListingDescription() != null) {
            existingListing.setListingDescription(listingDTO.getListingDescription());
        }
        if (listingDTO.getListingPricePerNight() != null) {
            existingListing.setListingPricePerNight(listingDTO.getListingPricePerNight());
        }
        if (listingDTO.getGuestLimit() != null) {
            existingListing.setListingGuestLimit(listingDTO.getGuestLimit());
        }
        if (listingDTO.getListingImages() != null) {
            existingListing.setListingImages(listingDTO.getListingImages());
        }

        Listing updatedListing = listingRepository.save(existingListing);
        return convertToDTO(updatedListing);
    }

    public void deleteListing (String listingId){
        if (!listingRepository.existsById(listingId)) {
            throw new ResourceNotFoundException("Listing not found");
        }
        listingRepository.deleteById(listingId);
    }


    //-------Hälp Mäthodz---------
    private ListingResponse convertToDTO (Listing listing){
        ListingResponse response = new ListingResponse();

        response.setListingId(listing.getListingId());
        response.setHostId(listing.getHost().getId());
        response.setHostName(listing.getHost().getUsername());
        response.setListingTitle(listing.getListingTitle());
        response.setListingDescription(listing.getListingDescription());
        response.setGuestLimit(listing.getListingGuestLimit());
        response.setListingPricePerNight(listing.getListingPricePerNight());
        response.setListingImages(listing.getListingImages());

        return response;
    }
}

