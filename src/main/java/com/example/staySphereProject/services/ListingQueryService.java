package com.example.staySphereProject.services;

import com.example.staySphereProject.converters.ListingDTOConverter;
import com.example.staySphereProject.dto.ListingResponse;
import com.example.staySphereProject.exeptions.ResourceNotFoundException;
import com.example.staySphereProject.models.Listing;
import com.example.staySphereProject.models.User;
import com.example.staySphereProject.repository.ListingRepository;
import com.example.staySphereProject.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ListingQueryService {

    private final ListingRepository listingRepository;
    private final UserRepository userRepository;
    private final ListingDTOConverter converter;

    public ListingQueryService(ListingRepository listingRepository, UserRepository userRepository, ListingDTOConverter converter) {
        this.listingRepository = listingRepository;
        this.userRepository = userRepository;
        this.converter = converter;

    }

    //find listings by price range
    public List<ListingResponse> findByPriceRange(Double minPrice, Double maxPrice) {
        // Validate price parameters
        validatePriceRange(minPrice, maxPrice);

        // Query the repository
        List<Listing> listings = listingRepository.findListingByListingPricePerNight(minPrice, maxPrice);

        // Check if results were found
        if (listings.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No listings found between " + minPrice + " and " + maxPrice);
        }

        // Convert to response DTOs
        return listings.stream()
                .map(converter::toResponse)
                .collect(Collectors.toList());
    }

    public List<ListingResponse> findByHostId(String hostId) {
        // Validate that the host exists
        User host = userRepository.findById(hostId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + hostId));

        // Query listings for this host
        List<Listing> listings = listingRepository.findByHostId(hostId);

        // Convert to response DTOs
        return listings.stream()
                .map(converter::toResponse)
                .collect(Collectors.toList());
    }

    //Validates price range
    private void validatePriceRange(Double minPrice, Double maxPrice) {
        if (minPrice == null || maxPrice == null) {
            throw new IllegalArgumentException("Price range cannot contain null values");
        }
        if (minPrice < 0 || maxPrice < 0) {
            throw new IllegalArgumentException("Prices cannot be negative");
        }
        if (minPrice > maxPrice) {
            throw new IllegalArgumentException("Minimum price cannot be greater than maximum price");
        }
    }








}
