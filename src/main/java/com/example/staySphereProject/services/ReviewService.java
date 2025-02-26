package com.example.staySphereProject.services;

import com.example.staySphereProject.models.Review;
import com.example.staySphereProject.repository.ListingRepository;
import com.example.staySphereProject.repository.ReviewRepository;
import com.example.staySphereProject.repository.UserRepository;
import org.springframework.stereotype.Service;

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

    public Review createReview(Review review) {
        /**if(review.getComment() == null || review.getComment().isEmpty()) {
            throw new IllegalArgumentException("Review comment cannot be empty");
        }**/
        reviewRepository.save(review);
        return review;
    }

}
