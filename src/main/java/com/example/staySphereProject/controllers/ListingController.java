package com.example.staySphereProject.controllers;

import com.example.staySphereProject.dto.ListingDTO;
import com.example.staySphereProject.dto.ListingResponse;
import com.example.staySphereProject.models.Listing;
import com.example.staySphereProject.models.User;
import com.example.staySphereProject.repository.ListingRepository;
import com.example.staySphereProject.repository.UserRepository;
import com.example.staySphereProject.services.ListingService;
import com.example.staySphereProject.services.UserService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/listing")
public class ListingController {


    private final ListingService listingService;


    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }



    @PostMapping("/register/{userId}")
    public ResponseEntity<ListingResponse> createListing (@Valid @RequestBody ListingDTO listingDTO, @PathVariable String userId) {
        ListingResponse newListing = listingService.createListing(listingDTO, userId);
        return new ResponseEntity<>(newListing, HttpStatus.CREATED);
    }



    @GetMapping("/getall")
    public ResponseEntity<List<Listing>> getAllListings() {
        List<Listing> existingListings = listingService.getAllListings();
        return ResponseEntity.ok(existingListings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Listing> getListingById(@PathVariable String id) {
        Optional<Listing> listing = listingService.getListingById(id);
        return listing.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/patch/{id}")
    public ResponseEntity<Listing> patchListing(@PathVariable String id, @RequestBody Listing listing) {
        Listing updatedListing = listingService.patchListing(listing, id);
        return new ResponseEntity<>(updatedListing, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListing(@PathVariable String id) {
        listingService.deleteListing(id);
        return ResponseEntity.noContent().build();
    }









}
