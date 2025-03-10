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
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    public List<ReviewResponse> getReviewById(String Id) {

        return getReviewsByListingId(Id);
    }



    public Review patchReview(String id, Review review) {
        Review existingReview = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Review Not Found"));

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


    private ListingResponse convertToDTO(Listing listing) {
        // Säkerställ att authentication är korrekt
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            throw new IllegalArgumentException("User is not authenticated");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        ListingResponse response = new ListingResponse();
        response.setListingId(listing.getListingId());
        response.setHostId(listing.getHost() != null ? listing.getHost().getId() : "Unknown Host");
        response.setHostName(listing.getHost() != null ? listing.getHost().getUsername() : "Unknown Host");
        response.setListingTitle(listing.getListingTitle());
        response.setListingDescription(listing.getListingDescription());
        response.setGuestLimit(listing.getListingGuestLimit());
        response.setListingPricePerNight(listing.getListingPricePerNight());
        response.setListingImages(listing.getListingImages() != null ? listing.getListingImages() : new ArrayList<>());

        // Hämta alla reviews för listing
        List<Review> reviews = reviewRepository.findByListingReviewed(listing);
        List<ReviewResponse> reviewResponses = new ArrayList<>();

        for (Review review : reviews) {
            ReviewResponse reviewResponse = convertToReviewDTO(review);
            reviewResponses.add(reviewResponse);
        }

        response.setReviews(reviewResponses);

        return response;
    }



    public List<ReviewResponse> getReviewsByListingId(String Id) {
        Listing listing = listingRepository.findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found"));

        List<Review> reviews = reviewRepository.findByListingReviewed(listing);

        return reviews.stream()
                .map(this::convertToReviewDTO)

                .collect(Collectors.toList());
    }






}

