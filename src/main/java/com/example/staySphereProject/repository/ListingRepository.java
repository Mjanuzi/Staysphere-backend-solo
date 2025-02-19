package com.example.staySphereProject.repository;

import com.example.staySphereProject.models.Listing;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ListingRepository extends MongoRepository<Listing, String> {

}
