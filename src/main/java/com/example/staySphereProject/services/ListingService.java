package com.example.staySphereProject.services;

import com.example.staySphereProject.exeptions.ResourceNotFoundException;
import com.example.staySphereProject.models.Listing;
import com.example.staySphereProject.repository.ListingRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class ListingService {
    private final ListingRepository listingRepository;

    public ListingService(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    public Listing registerListing(Listing listing) {

       return listingRepository.save(listing);
    }
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
}
