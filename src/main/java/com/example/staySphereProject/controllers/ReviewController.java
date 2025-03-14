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


    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;

    }

    //Posta en review
    @PostMapping("/user/{id}")
    public ResponseEntity<?> registerReview (@Valid @RequestBody ReviewRequest reviewRequest,@PathVariable String id) {
        ReviewResponse newReview = reviewService.createReview(reviewRequest, id );
        return ResponseEntity.status(HttpStatus.CREATED).body(newReview);

    }
    //Hämta alla reviews
    @GetMapping("/all")
    public ResponseEntity<List<Review>> getAllReviews() {
        List<Review> allReviews = reviewService.getAllReviews();
        return ResponseEntity.ok(allReviews);
    }
    //Hämta en review
    @GetMapping("/all/{Id}")
    public ResponseEntity<List<ReviewResponse>> getReviewById(@PathVariable String Id) {
        List<ReviewResponse> reviews = reviewService.getReviewsByListingId(Id);
        return ResponseEntity.ok(reviews);

    }
    //Uppdatera en review
    @PatchMapping("/patch/{id}")
    public ResponseEntity<ReviewResponse> patchReview(@PathVariable String id, @RequestBody Review review) {
        ReviewResponse updatedReview = reviewService.patchReview(id, review);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    //Radera en review
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable String id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

}
