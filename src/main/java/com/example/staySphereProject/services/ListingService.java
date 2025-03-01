package com.example.staySphereProject.services;

import com.example.staySphereProject.dto.ListingDTO;
import com.example.staySphereProject.dto.ListingResponse;
import com.example.staySphereProject.exeptions.ResourceNotFoundException;
import com.example.staySphereProject.models.Listing;
import com.example.staySphereProject.models.Review;
import com.example.staySphereProject.models.User;
import com.example.staySphereProject.repository.ListingRepository;
import com.example.staySphereProject.repository.ReviewRepository;
import com.example.staySphereProject.repository.UserRepository;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Optional;

@Service
public class ListingService {
    private final ListingRepository listingRepository;
    private final UserRepository userRepository;
    //private final ReviewRepository reviewRepository;


    public ListingService(ListingRepository listingRepository, UserRepository userRepository) {
        this.listingRepository = listingRepository;
        this.userRepository = userRepository;
    }





    //get listing by id
    public Optional<Listing> getListingById(String id) {
        return listingRepository.findById(id);
    }





  /*  public Listing patchListing(String id, Listing listing) {
        Listing existingListing = listingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));

        if (listing.getListingPricePerNight() != 0){
            existingListing.getListingPricePerNight(listing.setListingPricePerNight());
        }
        if (listing.isListingActive() != false ){
            existingListing.get().setListingActive(listing.isListingActive());
        }
        if (listing.getListingTitle() != null){
            existingListing.get().setListingTitle(listing.getListingTitle());
        }
        if (listing.getListingDescription() != null){
            existingListing.get().setListingDescription(listing.getListingDescription());
        }
        if (listing.isBooked() != false){
            existingListing.get().setBooked(listing.isBooked());
        }
        if (listing.getAvailable() != null) {
            existingListing.get().setAvailable(listing.getAvailable());
        }

        if (listing.getListingImages() != null) {
            existingListing.get().setListingImages(listing.getListingImages());
        }
        if (listing.getListingGuestLimit() != null){
            existingListing.get().setListingGuestLimit(listing.getListingGuestLimit());
        }
        if (listing.getHost() != null){
            existingListing.get().setHost(listing.getHost());
        }

        Listing savedListing = listingRepository.save(existingListing.get());
        return ResponseEntity.ok(savedListing);

}*/
    public void deleteProduct(String id) {
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        listingRepository.delete(listing);
    }





    //Register listing
    public ListingResponse createListing(ListingDTO listingDTO) {
        User user = userRepository.findById(listingDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));


        //Creating new listing
        Listing listing = new Listing();

        listing.setHost(user);
        listing.setListingTitle(listingDTO.getListingTitle());
        listing.setListingDescription(listingDTO.getListingDescription());
        listing.setListingPricePerNight(listingDTO.getPricePerNight());
        listing.setListingGuestLimit(listingDTO.getGuestLimit());
        //listing.getListingImages().addAll(listingDTO.getListingImages());


        //standard values when creating an object
        listing.setListingActive(true);
        listing.setBooked(false);
        listing.setAvailable(new ArrayList<>());

        Listing savedListing = listingRepository.save(listing);
        //return convertToDTO(listingRepository.save(listing));
        return convertToDTO(savedListing);
    }



    //-------Hälp Mäthoder---------






    private ListingResponse convertToDTO(Listing listing) {
        ListingResponse listingResponse = new ListingResponse();

        listingResponse.setListingId(listing.getListingId());
        listingResponse.setListingTitle(listing.getListingTitle());
        listingResponse.setListingDescription(listing.getListingDescription());
        listingResponse.setGuestLimit(listing.getListingGuestLimit());
        listingResponse.setPricePerNight(listing.getListingPricePerNight());
        listingResponse.setListingDescription(listing.getListingDescription());

        return listingResponse;
    }
}
