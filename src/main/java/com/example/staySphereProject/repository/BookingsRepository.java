package com.example.staySphereProject.repository;

import com.example.staySphereProject.models.Bookings;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookingsRepository extends MongoRepository<Bookings, String> {

    List<Bookings> findByUserId(String userId);

    List<Bookings> findByListingIdIn(List<String> listingIds);

}
