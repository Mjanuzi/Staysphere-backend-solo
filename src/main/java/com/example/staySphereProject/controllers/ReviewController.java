package com.example.staySphereProject.controllers;

import com.example.staySphereProject.models.Review;
import com.example.staySphereProject.repository.ReviewRepository;
import com.example.staySphereProject.services.ReviewService;
import com.example.staySphereProject.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewRepository reviewRepository;
    private final UserService userService;

    public ReviewController(ReviewService reviewService, ReviewRepository reviewRepository, UserService userService) {
        this.reviewService = reviewService;
        this.reviewRepository = reviewRepository;
        this.userService = userService;
    }

    /**@PostMapping("/user")
    public ResponseEntity<?> registerReview (@Valid @RequestBody Review review) {
        Review createdReview = reviewRepository

    }**/
}
