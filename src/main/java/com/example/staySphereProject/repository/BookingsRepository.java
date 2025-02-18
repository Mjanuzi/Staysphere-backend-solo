package com.example.staySphereProject.repository;

import com.example.staySphereProject.models.Bookings;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookingsRepository extends MongoRepository<Bookings, String> {
}
