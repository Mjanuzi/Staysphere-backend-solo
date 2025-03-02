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


    private final UserRepository userRepository;
    private final ListingRepository listingRepository;
    private final ListingService listingService;
    private final UserService userService;

    public ListingController(ListingService listingService, UserService userService, UserRepository userRepository, ListingRepository listingRepository) {
        this.userService = userService;
        this.listingService = listingService;
        this.userRepository = userRepository;
        this.listingRepository = listingRepository;
    }


   /*@PostMapping("/register/{userId}")
    public ResponseEntity<Listing> registerListing(@Valid @RequestBody Listing listing, @PathVariable String userId) {
        Listing registerListing = listingService.registerListing(listing);
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return new ResponseEntity<>(registerListing, HttpStatus.CREATED);
    }*/

    @PostMapping("/register/{userId}")
    public ResponseEntity<ListingResponse> createListing (@Valid @RequestBody ListingDTO listingDTO, @PathVariable String userId) {
        ListingResponse newListing = listingService.createListing(listingDTO, userId);
        return new ResponseEntity<>(newListing, HttpStatus.CREATED);
    }
















    @GetMapping("/getall")
    public ResponseEntity<List<Listing>> getAllListings() {
        List<Listing> existingListings = listingRepository.findAll();
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
        Optional<Listing> existingListing = Optional.of(listingRepository.findById(id))
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Listing not found"));


        if (listing.getListingPricePerNight() != 0){
            existingListing.get().setListingPricePerNight(listing.getListingPricePerNight());
        }
        if (listing.isListingActive() != false ){
            existingListing.get().setListingActive(listing.isListingActive());
        }
        if (listing.getListingTitle() != null){
            existingListing.get().setListingTitle(listing.getListingTitle());
        }
        if (listing.getListingDescription() != null){
            existingListing.get().setListingDescription(listing.getListingDescription());
        }
        /*if (listing.isBooked() != false){
            existingListing.get().setBooked(listing.isBooked());
        }*/
        if (listing.getAvailable() != null) {
            existingListing.get().setAvailable(listing.getAvailable());
        }

        if (listing.getListingImages() != null) {
            existingListing.get().setListingImages(listing.getListingImages());
        }
        if (listing.getListingGuestLimit() != null){
            existingListing.get().setListingGuestLimit(listing.getListingGuestLimit());
        }
        if (listing.getHost() != null){
            existingListing.get().setHost(listing.getHost());
        }

        Listing savedListing = listingRepository.save(existingListing.get());
        return ResponseEntity.ok(savedListing);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListing(@PathVariable String id) {
        listingService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }









}
