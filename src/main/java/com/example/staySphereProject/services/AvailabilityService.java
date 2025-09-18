package com.example.staySphereProject.services;

import com.example.staySphereProject.dto.AvailabilityRequest;
import com.example.staySphereProject.dto.AvailabilityResponse;
import com.example.staySphereProject.exeptions.ResourceNotFoundException;
import com.example.staySphereProject.models.Listing;
import com.example.staySphereProject.repository.ListingRepository;
import com.example.staySphereProject.util.CheckAuthentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class AvailabilityService {

    private final ListingRepository listingRepository;
    private final DateRangeService dateRangeService;
    private final CheckAuthentication checkAuthentication;

    public AvailabilityService(ListingRepository listingRepository, DateRangeService dateRangeService, CheckAuthentication checkAuthentication) {
        this.listingRepository = listingRepository;
        this.dateRangeService = dateRangeService;
        this.checkAuthentication = checkAuthentication;
    }

    public Listing addAvailbility(String listingId, AvailabilityRequest request) {
        //Find the listing
        Listing listing = findListingOrThrow(listingId);

        // Check authentication (user must be the host)
        checkAuthentication.validateListingOwned(listing);

        // Validate the date range using DateRangeService
        dateRangeService.validateDateRange(request.getStartDate(), request.getEndDate());

        // Generate the date range
        List<LocalDate> datesToAdd = dateRangeService.generateDateRange(
                request.getStartDate(), request.getEndDate());

        // Add dates to availability (using Set to avoid duplicates)
        Set<LocalDate> uniqueDates = new HashSet<>(listing.getAvailable());
        uniqueDates.addAll(datesToAdd);
        listing.setAvailable(new ArrayList<>(uniqueDates));

        // Save and return
        return listingRepository.save(listing);
    }

    //"https://stackoverflow.com/questions/51513447/is-hibernate-transactionalreadonly-true-on-read-query-a-bad-practice"
    @Transactional(readOnly = true)
    public List<AvailabilityResponse> getAvailabilityForListing(String listingId) {
        Listing listing = findListingOrThrow(listingId);
        return dateRangeService.convertToAvailabilityRanges(listing.getAvailable());
    }

    @Transactional(readOnly = true)
    public boolean isDateRangeAvailable(String listingId, LocalDate startDate, LocalDate endDate) {
        Listing listing = findListingOrThrow(listingId);

        // Generate the requested date range
        List<LocalDate> requestedDates = dateRangeService.generateDateRange(startDate, endDate);

        // Check if all requested dates are available
        return listing.getAvailable().containsAll(requestedDates);
    }


    // Method to help find a specific listing
    private Listing findListingOrThrow(String listingId) {
        return listingRepository.findById(listingId)
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found with ID: " + listingId));
    }




}
