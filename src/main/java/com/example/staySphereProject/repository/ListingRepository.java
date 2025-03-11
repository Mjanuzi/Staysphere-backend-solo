package com.example.staySphereProject.repository;

import com.example.staySphereProject.models.Listing;
import com.example.staySphereProject.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ListingRepository extends MongoRepository<Listing, String> {

    List<Listing> findByHost(User host);

    List<Listing> findByHostId(String userId);

    Optional<Listing> findListingWithReviewsById(String listingId);

}
