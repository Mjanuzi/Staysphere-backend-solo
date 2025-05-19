package com.example.staySphereProject.repository;

import com.example.staySphereProject.models.Bookings;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BookingsRepository extends MongoRepository<Bookings, String> {

    List<Bookings> findByUserId(String userId);

    List<Bookings> findByListingIdIn(List<String> listingIds);

    // Hämta booking baserat på användare och listing
    Optional<Bookings> findByUserIdAndListingId(String userId, String listingId);

    List<Bookings> findByListingId(String listingId);

}
