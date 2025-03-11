package com.example.staySphereProject.repository;

import com.example.staySphereProject.dto.ListingResponse;
import com.example.staySphereProject.models.Listing;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ListingRepository extends MongoRepository<Listing, String> {
    List<Listing> findByHostId(String hostId);

    Optional<Listing> findListingWithReviewsById(String listingId);

    @Query("{'listingPricePerNight': {$gte: ?0, $lte: ?1}}")
    List<Listing> findListingByListingPricePerNight(double min, double max);

}
