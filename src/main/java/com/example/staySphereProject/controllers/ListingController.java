package com.example.staySphereProject.controllers;

import com.example.staySphereProject.dto.AvailabilityRequest;
import com.example.staySphereProject.dto.ListingDTO;
import com.example.staySphereProject.dto.ListingResponse;
import com.example.staySphereProject.dto.AvailabilityResponse;

import com.example.staySphereProject.dto.ListingResponseGetAll;

import com.example.staySphereProject.models.Listing;
import com.example.staySphereProject.services.DateRangeService;
import com.example.staySphereProject.services.ListingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/listing")
public class ListingController {

    private final ListingService listingService;
    private final DateRangeService dateRangeService;

    public ListingController(ListingService listingService, DateRangeService dateRangeService) {
        this.listingService = listingService;
        this.dateRangeService = dateRangeService;
    }


    @PostMapping("/create")
    public ResponseEntity<ListingResponse> createListing (@Valid @RequestBody ListingDTO listingDTO) {
        ListingResponse newListing = listingService.createListing(listingDTO);
        return new ResponseEntity<>(newListing, HttpStatus.CREATED);
    }

    @PostMapping("/listings/{listingId}/availability")
    //@PreAuthorize("hasRole('HOST')")
    public ResponseEntity<Listing> addAvailability(@PathVariable String listingId,
                                                   @RequestBody AvailabilityRequest request)
    {
            //@AuthenticationPrincipal UserDetails userDetail) {
        Listing updatedListing = listingService.addAvailability(listingId, request);
        return new ResponseEntity<>(updatedListing, HttpStatus.OK);
    }

    @GetMapping("/listings/{listingId}/availability")
    public ResponseEntity<List<AvailabilityResponse>> getAvailability(@PathVariable String listingId) {
        List<AvailabilityResponse> availabilityList = listingService.g()
        return ResponseEntity.ok(availabilityList);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<ListingResponseGetAll>> getAllListings() {
        List<ListingResponseGetAll> listings = listingService.getAllListings();

        return ResponseEntity.ok(listings);
    }

  /* @GetMapping("/gethostbyid/{userId}")
    public ResponseEntity<List<ListingResponse>> getListingByHostId(@PathVariable String userId) {
        List<ListingResponse> listings = listingService.getListingByHostId(userId);
        return ResponseEntity.ok(listings);

    }*/

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

    /*@GetMapping("/all/pricebetween")
    public ResponseEntity<List<ListingResponse>> getListingsByPrice(
            @RequestParam("minPrice") Double minPrice,
            @RequestParam("maxPrice") Double maxPrice){
        List<ListingResponse> listings = listingService.getListingByPriceBetween(minPrice,maxPrice);

        return ResponseEntity.ok(listings);
    }

    @GetMapping("/my-listings")
    public ResponseEntity<List<ListingResponse>> getMyListings(
        @AuthenticationPrincipal UserDetails userDetails) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetailsFromAuth = (UserDetails) authentication.getPrincipal();
        String userId = userDetailsFromAuth.getUsername();
        
        List<ListingResponse> listings = listingService.getListingByHostId(userId);
        return ResponseEntity.ok(listings);
    }*/
}
