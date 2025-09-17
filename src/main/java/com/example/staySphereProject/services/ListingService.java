package com.example.staySphereProject.services;
import com.example.staySphereProject.converters.ListingDTOConverter;
import com.example.staySphereProject.dto.*;
import com.example.staySphereProject.exeptions.ResourceNotFoundException;
import com.example.staySphereProject.models.Listing;
import com.example.staySphereProject.models.Residence;
import com.example.staySphereProject.models.User;
import com.example.staySphereProject.repository.ListingRepository;
import com.example.staySphereProject.util.CheckAuthentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ListingService {
    private final ListingRepository listingRepository;
    private final ResidenceProcessor residenceProcessor;
    private final CheckAuthentication checkAuthentication;
    private final ListingDTOConverter converter;

    public ListingService(ListingRepository listingRepository, ResidenceProcessor residenceProcessor,
                          CheckAuthentication checkAuthentication, ListingDTOConverter converter) {
        this.listingRepository = listingRepository;
        this.residenceProcessor = residenceProcessor;
        this.checkAuthentication = checkAuthentication;
        this.converter = converter;
    }

    @Transactional
    public Listing addAvailability(String listingId, AvailabilityRequest request) {
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found"));

        checkAuthentication.validateListingOwned(listing);
        // Validate date range
        validateDateRange(request.getStartDate(), request.getEndDate());

        // Generate dates
        List<LocalDate> datesToAdd = generateDateRange(request.getStartDate(), request.getEndDate());

        Set<LocalDate> uniqueDates = new HashSet<>(listing.getAvailable());
        uniqueDates.addAll(datesToAdd);
        listing.setAvailable(new ArrayList<>(uniqueDates));

        return listingRepository.save(listing);
    }

    private void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        if (ChronoUnit.DAYS.between(startDate, endDate) >= 90) {
            throw new IllegalArgumentException("Date rangfe cannot exceed 90 days");
        }
    }

    private List<LocalDate> generateDateRange(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> dates = new ArrayList<>();
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            dates.add(currentDate);
            currentDate = currentDate.plusDays(1);
        }
        return dates;
    }

    //Register listing
    public ListingResponse createListing(ListingDTO listingDTO) {

        return residenceProcessor.processListing(listingDTO);
    }

    public List<ListingResponseGetAll> getAllListings () {
        return listingRepository.findAll().stream()
                .map(converter::toGetAllResponse)
                .collect(Collectors.toList());
    }

    //get listing by id
    public ListingResponse getListingById (String listingId){
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found"));
        return converter.toResponse(listing);
    }

    public ListingResponse patchListing (String listingId, ListingDTO listingDTO){

        Listing existListing = findListingOrThrow(listingId);
        checkAuthentication.validateListingOwned(existListing);

        return residenceProcessor.updateListing(listingId, listingDTO);

    }

    public void deleteListing (String listingId){

        Listing existingListing = findListingOrThrow(listingId);
        checkAuthentication.validateListingOwned(existingListing);
        listingRepository.delete(existingListing);
    }

    //-------Hälp Mäthodz---------
    /*private ListingResponse convertToDTO (Listing listing){
        ListingResponse response = new ListingResponse();
        response.setListingId(listing.getListingId());

        if(listing instanceof Residence){
            Residence residence = (Residence) listing;
            response.setHostId(residence.getHost().getId());
            response.setHostName(residence.getHost().getUsername());
        }

        response.setListingTitle(listing.getListingTitle());
        response.setListingDescription(listing.getListingDescription());
        response.setGuestLimit(listing.getListingGuestLimit());
        response.setListingPricePerNight(listing.getListingPricePerNight());
        response.setListingImages(listing.getListingImages());
        response.setAvailable(listing.getAvailable());
        response.setLocation(listing.getLocation());
        response.setListingActive(listing.isListingActive());

        return response;
    }

    private ListingResponseGetAll convertToDTOGetAll(Listing listing){
        ListingResponseGetAll response = new ListingResponseGetAll();
        response.setListingId(listing.getListingId());

        if(listing instanceof Residence){
            Residence residence = (Residence) listing;
            response.setHostName(residence.getHost().getUsername());
        }
        response.setListingTitle(listing.getListingTitle());
        response.setListingPricePerNight(listing.getListingPricePerNight());
        response.setListingImages(listing.getListingImages());
        response.setLocation(listing.getLocation());
        response.setListingActive(listing.isListingActive());


        return response;
    }*/
    /*
    public List<ListingResponse> getListingByPriceBetween(Double minPrice, Double maxPrice){
        if (minPrice < 0 || maxPrice < 0) {
            throw new ResourceNotFoundException("Listing Price cannot be negative");
        }
        if (minPrice > maxPrice) {
            throw new ResourceNotFoundException("Listing Price cannot be greater than maxPrice");
        }
        List<Listing> listings = listingRepository.findListingByListingPricePerNight(minPrice, maxPrice);
        if (listings.isEmpty()) {
            throw new ResourceNotFoundException("Did not find any listings between " + minPrice + " and " + maxPrice);
        }
        return listingRepository.findListingByListingPricePerNight(minPrice,maxPrice).stream()
                .map(converter::toResponse)
                .collect(Collectors.toList());
    }


    public List<ListingResponse> getListingByHostId(String hostId) {
                userRepository.findById(hostId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return residenceRepository.findByHostId(hostId).stream()
                .map(converter::toResponse)
                .collect(Collectors.toList());
    }*/

    private Listing findListingOrThrow(String listingId) {
        return listingRepository.findById(listingId)
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found with ID: " + listingId));
    }

    /**
     * Get availability for a listing
     * 
     * This method converts the individual available dates in the listing to
     * a list of date ranges (start date and end date pairs) that can be used
     * by the frontend calendar.
     *
     * @param listingId the listing ID
     * @return List of availability periods
     */
    public List<AvailabilityResponse> getAvailabilityForListing(String listingId) {
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found"));
        
        List<LocalDate> availableDates = listing.getAvailable();
        if (availableDates == null || availableDates.isEmpty()) {
            return new ArrayList<>();
        }
        
        // Sort the dates in ascending order
        Collections.sort(availableDates);
        
        // Convert individual dates to date ranges
        List<AvailabilityResponse> dateRanges = new ArrayList<>();
        LocalDate rangeStart = availableDates.get(0);
        LocalDate rangeEnd = rangeStart;
        
        for (int i = 1; i < availableDates.size(); i++) {
            LocalDate currentDate = availableDates.get(i);
            // If the current date is one day after the previous end date, extend the range
            if (currentDate.isEqual(rangeEnd.plusDays(1))) {
                rangeEnd = currentDate;
            } else {
                // This date is not consecutive, so close the current range and start a new one
                dateRanges.add(new AvailabilityResponse(rangeStart, rangeEnd));
                rangeStart = currentDate;
                rangeEnd = currentDate;
            }
        }
        
        // Add the last range
        dateRanges.add(new AvailabilityResponse(rangeStart, rangeEnd));
        
        return dateRanges;
    }
}
