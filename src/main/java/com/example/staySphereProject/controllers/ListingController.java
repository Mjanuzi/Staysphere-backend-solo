package com.example.staySphereProject.controllers;

import com.example.staySphereProject.dto.ListingDTO;
import com.example.staySphereProject.dto.ListingResponse;
import com.example.staySphereProject.services.ListingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class ListingController {


    private final ListingService listingService;


    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }



    @PostMapping("/listings")
    public ResponseEntity<ListingResponse> createListing (@Valid @RequestBody ListingDTO listingDTO) {
        ListingResponse newListing = listingService.createListing(listingDTO);
        return new ResponseEntity<>(newListing, HttpStatus.CREATED);
    }



    @GetMapping("/listings/all")
    public ResponseEntity<List<ListingResponse>> getAllListings() {
        List<ListingResponse> listings = listingService.getAllListings();
        return ResponseEntity.ok(listings);
    }

    @GetMapping("/listings/{listingId}")
    public ResponseEntity<ListingResponse> getListingById(@PathVariable String listingId) {
        ListingResponse listing = listingService.getListingById(listingId);
        return ResponseEntity.ok(listing);
    }

    @PatchMapping("/listings/patch/{listingId}")
    public ResponseEntity<ListingResponse> patchListing(@PathVariable String listingId, @RequestBody ListingDTO listingDTO) {
        ListingResponse updatedListing = listingService.patchListing(listingId, listingDTO);
        return new ResponseEntity<>(updatedListing, HttpStatus.OK);
    }


    @DeleteMapping("/listings/{listingId}")
    public ResponseEntity<Void> deleteListing(@PathVariable String listingId) {
        listingService.deleteListing(listingId);
        return ResponseEntity.noContent().build();
    }









}
