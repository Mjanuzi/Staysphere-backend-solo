package com.example.staySphereProject.repository;

import com.example.staySphereProject.dto.ListingResponse;
import com.example.staySphereProject.models.Listing;
import com.example.staySphereProject.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ListingRepository extends MongoRepository<Listing, String> {
    Optional<Listing> findListingWithReviewsById(String listingId);
    @Query("{'listingPricePerNight': {$gte: ?0, $lte: ?1}}")
    List<Listing> findListingByListingPricePerNight(double min, double max);

    // Query for finding listings by host ID
    // This works for Residence listings that have a host field
    @Query("{'host.$id': ?0}")
    List<Listing> findByHostId(String hostId);
}
