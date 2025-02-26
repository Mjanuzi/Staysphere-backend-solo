package com.example.staySphereProject.controllers;

import com.example.staySphereProject.dto.ReviewRequest;
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
    public ResponseEntity<?> registerReview (@Valid @RequestBody ReviewRequest reviewRequest) {
        Review newReview = reviewService.createReview(reviewRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newReview);

    }
}
