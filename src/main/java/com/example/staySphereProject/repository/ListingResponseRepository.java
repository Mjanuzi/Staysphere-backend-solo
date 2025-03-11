package com.example.staySphereProject.repository;

import com.example.staySphereProject.dto.ListingResponse;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ListingResponseRepository extends MongoRepository<ListingResponse, String> {

}
