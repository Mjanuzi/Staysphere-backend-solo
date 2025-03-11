package com.example.staySphereProject.controllers;

import com.example.staySphereProject.dto.ReviewRequest;
import com.example.staySphereProject.dto.ReviewResponse;
import com.example.staySphereProject.models.Listing;
import com.example.staySphereProject.models.Review;
import com.example.staySphereProject.models.User;
import com.example.staySphereProject.repository.ReviewRepository;
import com.example.staySphereProject.repository.UserRepository;
import com.example.staySphereProject.services.ReviewService;
import com.example.staySphereProject.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public ReviewController(ReviewService reviewService, ReviewRepository reviewRepository, UserService userService, UserRepository userRepository) {
        this.reviewService = reviewService;
        this.reviewRepository = reviewRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/user/{id}")
    public ResponseEntity<?> registerReview (@Valid @RequestBody ReviewRequest reviewRequest,@PathVariable String id) {
        ReviewResponse newReview = reviewService.createReview(reviewRequest, id );
        return ResponseEntity.status(HttpStatus.CREATED).body(newReview);

    }
    @GetMapping("/all")
    public ResponseEntity<List<Review>> getAllReviews() {
        List<Review> allReviews = reviewService.getAllReviews();
        return ResponseEntity.ok(allReviews);
    }
    @GetMapping("/all/{Id}")
    public ResponseEntity<List<ReviewResponse>> getReviewById(@PathVariable String Id) {
        List<ReviewResponse> reviews = reviewService.getReviewsByListingId(Id);
        return ResponseEntity.ok(reviews);

    }

    @PatchMapping("/patch/{id}")
    public ResponseEntity<ReviewResponse> patchReview(@PathVariable String id, @RequestBody Review review) {
        ReviewResponse updatedReview = reviewService.patchReview(id, review);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable String id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

}
