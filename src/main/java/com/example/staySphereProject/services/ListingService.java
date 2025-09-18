package com.example.staySphereProject.services;
import com.example.staySphereProject.converters.ListingDTOConverter;
import com.example.staySphereProject.dto.*;
import com.example.staySphereProject.exeptions.ResourceNotFoundException;
import com.example.staySphereProject.models.Listing;
import com.example.staySphereProject.repository.ListingRepository;
import com.example.staySphereProject.util.CheckAuthentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ListingService {
    private final ListingRepository listingRepository;
    private final ResidenceProcessor residenceProcessor;
    private final CheckAuthentication checkAuthentication;
    private final ListingDTOConverter converter;

    public ListingService(ListingRepository listingRepository, ResidenceProcessor residenceProcessor,
                          CheckAuthentication checkAuthentication, ListingDTOConverter converter) {
        this.listingRepository = listingRepository;
        this.residenceProcessor = residenceProcessor;
        this.checkAuthentication = checkAuthentication;
        this.converter = converter;

    }
    //Register listing
    public ListingResponse createListing(ListingDTO listingDTO) {
        return residenceProcessor.processListing(listingDTO);
    }

    public List<ListingResponseGetAll> getAllListings () {
        return listingRepository.findAll().stream()
                .map(converter::toGetAllResponse)
                .collect(Collectors.toList());
    }

    //get listing by id
    public ListingResponse getListingById (String listingId){
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found"));
        return converter.toResponse(listing);
    }

    public ListingResponse patchListing (String listingId, ListingDTO listingDTO){

        Listing existListing = findListingOrThrow(listingId);
        checkAuthentication.validateListingOwned(existListing);
        return residenceProcessor.updateListing(listingId, listingDTO);

    }

    public void deleteListing (String listingId){
        Listing existingListing = findListingOrThrow(listingId);
        checkAuthentication.validateListingOwned(existingListing);
        listingRepository.delete(existingListing);
    }

    private Listing findListingOrThrow(String listingId) {
        return listingRepository.findById(listingId)
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found with ID: " + listingId));
    }
}
