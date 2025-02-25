package com.example.staySphereProject.controllers;

import com.example.staySphereProject.models.Listing;
import com.example.staySphereProject.services.ListingService;
import com.example.staySphereProject.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@RestController
@RequestMapping("/api/listing")
public class ListingController {

    private ListingService listingService;
    private UserService userService;

    public ListingController(ListingService listingService, UserService userService) {
        this.userService = userService;
        this.listingService = listingService;

    }

    @PostMapping("/register")
    public ResponseEntity<Listing> registerListing(@Valid @RequestBody Listing listing, @PathVariable String userId) {
        Listing registerListing = listingService.registerListing(listing);

        return new ResponseEntity<>(registerListing, HttpStatus.CREATED);
    }



}
