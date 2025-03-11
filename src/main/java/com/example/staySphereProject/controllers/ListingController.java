package com.example.staySphereProject.controllers;

import com.example.staySphereProject.dto.AvailabilityRequest;
import com.example.staySphereProject.dto.ListingDTO;
import com.example.staySphereProject.dto.ListingResponse;

import com.example.staySphereProject.dto.ListingResponseGetAll;

import com.example.staySphereProject.models.Listing;
import com.example.staySphereProject.services.ListingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/listing")
public class ListingController {


    private final ListingService listingService;


    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }


    @PostMapping("/create")
    public ResponseEntity<ListingResponse> createListing (@Valid @RequestBody ListingDTO listingDTO) {
        ListingResponse newListing = listingService.createListing(listingDTO);
        return new ResponseEntity<>(newListing, HttpStatus.CREATED);
    }




    @PostMapping("/listings/{listingId}/availability")
    //@PreAuthorize("hasRole('HOST')")
    public ResponseEntity<Listing> addAvailability(
            @PathVariable String listingId,
            @RequestBody AvailabilityRequest request) {
            //@AuthenticationPrincipal UserDetails userDetail) {
        Listing updatedListing = listingService.addAvailability(
                listingId,
                request
        );

        return new ResponseEntity<>(updatedListing, HttpStatus.OK);
    }


    @GetMapping("/getall")
    public ResponseEntity<List<ListingResponseGetAll>> getAllListings() {
        List<ListingResponseGetAll> listings = listingService.getAllListings();

        return ResponseEntity.ok(listings);
    }

   @GetMapping("/gethostbyid/{userId}")
    public ResponseEntity<List<ListingResponse>> getListingByHostId(@PathVariable String userId) {
        List<ListingResponse> listings = listingService.getListingByHostId(userId);
        return ResponseEntity.ok(listings);

    }
    @GetMapping("/getbyid/{listingId}")
    public ResponseEntity<ListingResponse> getListingById(@PathVariable String listingId) {
        ListingResponse listing = listingService.getListingById(listingId);
        return ResponseEntity.ok(listing);
    }

    @PatchMapping("/patch/{listingId}")
    public ResponseEntity<ListingResponse> patchListing(@PathVariable String listingId, @RequestBody ListingDTO listingDTO) {
        ListingResponse updatedListing = listingService.patchListing(listingId, listingDTO);
        return new ResponseEntity<>(updatedListing, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{listingId}")
    public ResponseEntity<Void> deleteListing(@PathVariable String listingId) {
        listingService.deleteListing(listingId);
        return ResponseEntity.noContent().build();
    }
    
    

    @GetMapping("/all/pricebetween")
    public ResponseEntity<List<Listing>> getListingsByPrice(
            @RequestParam("minPrice") Double minPrice,
            @RequestParam("maxPrice") Double maxPrice){
        List<Listing> listings = listingService.getListingByPriceBetween(minPrice,maxPrice);

        return ResponseEntity.ok(listings);
    }



}
