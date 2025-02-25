package com.example.staySphereProject.controllers;

import com.example.staySphereProject.models.Listing;
import com.example.staySphereProject.models.User;
import com.example.staySphereProject.repository.ListingRepository;
import com.example.staySphereProject.repository.UserRepository;
import com.example.staySphereProject.services.ListingService;
import com.example.staySphereProject.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping("/getall")
    public ResponseEntity<List<Listing>> getAllListings() {
        List<Listing> existingListings = listingRepository.findAll();
        return ResponseEntity.ok(existingListings);
    }
    @PostMapping("/register/{userId}")
    public ResponseEntity<Listing> registerListing(@Valid @RequestBody Listing listing, @PathVariable String userId) {
        Listing registerListing = listingService.registerListing(listing);
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        User user = userOptional.get();

        // Koppla listing till user
        listing.setHost(user);
        return new ResponseEntity<>(registerListing, HttpStatus.CREATED);
    }





}
