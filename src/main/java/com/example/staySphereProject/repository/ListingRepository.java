package com.example.staySphereProject.repository;

import com.example.staySphereProject.dto.ListingResponse;
import com.example.staySphereProject.models.Listing;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ListingRepository extends MongoRepository<Listing, String> {
    List<ListingResponse> findByHostId(String userId);

    Optional<Listing> findListingWithReviewsById(String listingId);

    List<Listing> findByListingPricePerNightBetween(double min, double max);

    List<Listing> findByListingGuestLimit(int guestLimit);

}
