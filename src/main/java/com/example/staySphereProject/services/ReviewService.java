package com.example.staySphereProject.services;

import com.example.staySphereProject.dto.ReviewRequest;
import com.example.staySphereProject.models.Listing;
import com.example.staySphereProject.models.Review;
import com.example.staySphereProject.models.User;
import com.example.staySphereProject.repository.ListingRepository;
import com.example.staySphereProject.repository.ReviewRepository;
import com.example.staySphereProject.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ListingRepository listingRepository;

    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository, ListingRepository listingRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.listingRepository = listingRepository;
    }

    public Review createReview(ReviewRequest reviewRequest) {
        // Hämta user från databasen
        User existingUser = userRepository.findById(reviewRequest.getUserReviewer())
                .orElseThrow(() -> new IllegalArgumentException("User Not Found"));

        //hämta listing från databasen
        Listing existingListing = listingRepository.findById(reviewRequest.getReviewedListing())
                .orElseThrow(() -> new IllegalArgumentException("Listing Not Found"));

        Review review = new Review();
        review.setUserReviewer(existingUser);
        review.setListingReviewed(existingListing);
        review.setComment(reviewRequest.getComment());
        review.setReviewRating(reviewRequest.getReviewRating());
        review.setReviewDateSet(LocalDateTime.now());

        //spara review
        return reviewRepository.save(review);

    }
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }


}
