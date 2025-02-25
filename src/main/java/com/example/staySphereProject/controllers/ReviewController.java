package com.example.staySphereProject.controllers;

import com.example.staySphereProject.models.Review;
import com.example.staySphereProject.repository.ReviewRepository;
import com.example.staySphereProject.services.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewRepository reviewRepository;
    private f

    public ReviewController(ReviewService reviewService, ReviewRepository reviewRepository) {
        this.reviewService = reviewService;
        this.reviewRepository = reviewRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerReview (Review review, String id) {

    }
}
