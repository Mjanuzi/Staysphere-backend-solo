package com.example.staySphereProject.repository;

import com.example.staySphereProject.models.Listing;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ListingRepository extends MongoRepository<Listing, String> {
    List<Listing> findByHostId(String userId);

}
