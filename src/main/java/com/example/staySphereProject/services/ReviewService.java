package com.example.staySphereProject.services;

import com.example.staySphereProject.dto.ReviewRequest;
import com.example.staySphereProject.dto.ReviewResponse;
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

    public ReviewResponse createReview(ReviewRequest reviewRequest, String id) {
        // Hämta user från databasen
        User existingUser = userRepository.findById(id)
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
        Review savedReview = reviewRepository.save(review);
        return convertToReviewDTO(savedReview);

    }
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review getReviewById(String id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Review Not Found"));
    }

    public Review patchReview(String id, Review review) {
        Review existingReview = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Review Not Found"));

        if(review.getUserReviewer() != null){
            existingReview.setUserReviewer(review.getUserReviewer());
        }
        if(review.getListingReviewed() != null){
            existingReview.setListingReviewed(review.getListingReviewed());
        }
        if(review.getComment() != null){
            existingReview.setComment(review.getComment());
        }
        if (review.getReviewRating() != null){
            existingReview.setReviewRating(review.getReviewRating());
        }
        if(review.getReviewDateSet() != null){
            existingReview.setReviewDateSet(review.getReviewDateSet());
        }
        if (review.getId() != null){
            existingReview.setId(id);
        }
        return reviewRepository.save(existingReview);
    }

    public void deleteReview(String id) {
        Review existingReview = reviewRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Review Not Found"));
        reviewRepository.deleteById(existingReview.getId());
    }


    private ReviewResponse convertToReviewDTO(Review review) {
        ReviewResponse reviewResponse = new ReviewResponse();

        reviewResponse.setReviewComment(review.getComment());
        reviewResponse.setReviewerUsername(review.getUserReviewer().getUsername());
        reviewResponse.setReviewedRating(review.getReviewRating());
        reviewResponse.setReviewedListing(review.getListingReviewed().getListingTitle());

        return reviewResponse;
    }




}
