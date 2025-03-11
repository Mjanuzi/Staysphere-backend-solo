package com.example.staySphereProject.repository;

import com.example.staySphereProject.models.Listing;
import com.example.staySphereProject.models.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, String> {

    List<Review> findByListingReviewed(Listing listingReviewed);


}
