package com.example.staySphereProject.services;

import com.example.staySphereProject.models.Listing;
import com.example.staySphereProject.repository.ListingRepository;
import org.springframework.stereotype.Service;

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

}
