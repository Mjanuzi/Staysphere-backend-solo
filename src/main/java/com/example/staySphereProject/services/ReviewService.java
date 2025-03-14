package com.example.staySphereProject.services;

import com.example.staySphereProject.dto.*;
import com.example.staySphereProject.exeptions.ResourceNotFoundException;
import com.example.staySphereProject.models.Listing;
import com.example.staySphereProject.models.Review;
import com.example.staySphereProject.models.User;
import com.example.staySphereProject.repository.ListingRepository;
import com.example.staySphereProject.repository.ReviewRepository;
import com.example.staySphereProject.repository.UserRepository;
import com.example.staySphereProject.util.CheckAuthentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ListingRepository listingRepository;
    private final CheckAuthentication checkAuthentication;

    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository, ListingRepository listingRepository, CheckAuthentication checkAuthentication) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.listingRepository = listingRepository;
        this.checkAuthentication = checkAuthentication;
    }

    public ReviewResponse createReview(ReviewRequest reviewRequest, String id) {
        // Hämta user från databasen

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        checkAuthentication.validateAuthenticatedUser(existingUser.getId());
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
    // Hämta alla reviews
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public ReviewResponse getReviewById(String Id) {
        Review review = reviewRepository.findById(Id)
                .orElseThrow(() -> new IllegalArgumentException("Review Not Found"));
        return convertToReviewDTO(review);
    }

    //Uppdatera en review med patch
    public ReviewResponse patchReview(String id, Review review) {
        Review existingReview = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Review Not Found"));

        //Validera så att rätt person får uppdatera en kommentar
        checkAuthentication.validateAuthenticatedUser(existingReview.getUserReviewer().getId());

        if (review.getUserReviewer() != null) {
            existingReview.setUserReviewer(review.getUserReviewer());
        }
        if (review.getListingReviewed() != null) {
            existingReview.setListingReviewed(review.getListingReviewed());
        }
        if (review.getComment() != null) {
            existingReview.setComment(review.getComment());
        }
        if (review.getReviewRating() != null) {
            existingReview.setReviewRating(review.getReviewRating());
        }
        if (review.getReviewDateSet() != null) {
            existingReview.setReviewDateSet(review.getReviewDateSet());
        }
        if (review.getId() != null) {
            existingReview.setId(id);
        }

        return convertToReviewDTO(existingReview);
    }

    // Radera en review
    public void deleteReview(String id) {
        Review existingReview = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Review Not Found"));

        //Validera så att rätt person får ta bort sin kommentar
        checkAuthentication.validateAuthenticatedUser(existingReview.getUserReviewer().getId());

        reviewRepository.deleteById(existingReview.getId());
    }

    //Konvertera DTO till response
    private ReviewResponse convertToReviewDTO(Review review) {
        ReviewResponse reviewResponse = new ReviewResponse();

        reviewResponse.setReviewComment(review.getComment());
        reviewResponse.setReviewerId(review.getUserReviewer().getId());
        reviewResponse.setReviewerUsername(review.getUserReviewer().getUsername());
        reviewResponse.setReviewedRating(review.getReviewRating());
        reviewResponse.setReviewedListing(review.getListingReviewed().toString());

        return reviewResponse;
    }

    //Hämta alla reviews som tillhör en listing
    public List<ReviewResponse> getReviewsByListingId(String Id) {
        Listing listing = listingRepository.findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found"));

        List<Review> reviews = reviewRepository.findByListingReviewed(listing);

        return reviews.stream()
                .map(this::convertToReviewDTO)

                .collect(Collectors.toList());
    }






}

