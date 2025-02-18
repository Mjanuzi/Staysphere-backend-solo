package com.example.staySphereProject.repository;

import com.example.staySphereProject.models.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReviewRepository extends MongoRepository<Review, String> {
}
