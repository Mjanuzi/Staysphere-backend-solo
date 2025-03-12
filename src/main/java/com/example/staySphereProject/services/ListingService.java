package com.example.staySphereProject.services;
import com.example.staySphereProject.dto.AvailabilityRequest;
import com.example.staySphereProject.dto.ListingDTO;
import com.example.staySphereProject.dto.ListingResponse;
import com.example.staySphereProject.dto.ListingResponseGetAll;
import com.example.staySphereProject.exeptions.ResourceNotFoundException;
import com.example.staySphereProject.models.Listing;
import com.example.staySphereProject.models.User;
import com.example.staySphereProject.repository.ListingRepository;
import com.example.staySphereProject.repository.UserRepository;
import com.example.staySphereProject.util.CheckAuthentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ListingService {
    private final ListingRepository listingRepository;
    private final UserRepository userRepository;
    private final CheckAuthentication checkAuthentication;
    //private final ReviewRepository reviewRepository;

    public ListingService(ListingRepository listingRepository,
                          UserRepository userRepository,
                          CheckAuthentication checkAuthentication) {
        this.listingRepository = listingRepository;
        this.userRepository = userRepository;
        this.checkAuthentication = checkAuthentication;
    }

    @Transactional
    public Listing addAvailability(String listingId, AvailabilityRequest request) {
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found"));

        checkAuthentication.validateAuthenticatedUser(listing.getHost().getId());
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

        User host = checkAuthentication.validateAuthenticatedUser(listingDTO.getHostId());

            //Creating new listing
            Listing listing = new Listing();
            listing.setHost(host);
            listing.setListingTitle(listingDTO.getListingTitle());
            listing.setListingDescription(listingDTO.getListingDescription());
            listing.setListingPricePerNight(listingDTO.getListingPricePerNight());
            listing.setListingGuestLimit(listingDTO.getGuestLimit());
            listing.setListingImages(listingDTO.getListingImages());

            //standard values when creating an object
            listing.setListingActive(true);
            listing.setAvailable(new ArrayList<>());
            listing.setReview(new ArrayList<>());

            Listing savedListing = listingRepository.save(listing);
            return convertToDTO(savedListing);
    }

    public List<ListingResponseGetAll> getAllListings () {
        return listingRepository.findAll().stream()
                .map(this::convertToDTOGetAll)
                .collect(Collectors.toList());
    }

    //get listing by id
    public ListingResponse getListingById (String listingId){
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found"));
        return convertToDTO(listing);
    }

    public ListingResponse patchListing (String listingId, ListingDTO listingDTO){
        //Check if the listing exists
        Listing existingListing = listingRepository.findById(listingId)
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found"));

        //checks if the logged-in user is the host of existingListing
        checkAuthentication.validateAuthenticatedUser(existingListing.getHost().getId());

        if (listingDTO.getHostId() != null) {
            User newHost = userRepository.findById(listingDTO.getHostId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            existingListing.setHost(newHost);
        }
        if (listingDTO.getListingTitle() != null) {
            existingListing.setListingTitle(listingDTO.getListingTitle());
        }
        if (listingDTO.getListingDescription() != null) {
            existingListing.setListingDescription(listingDTO.getListingDescription());
        }
        if (listingDTO.getListingPricePerNight() != null) {
            existingListing.setListingPricePerNight(listingDTO.getListingPricePerNight());
        }
        if (listingDTO.getGuestLimit() != null) {
            existingListing.setListingGuestLimit(listingDTO.getGuestLimit());
        }
        if (listingDTO.getListingImages() != null) {
            existingListing.setListingImages(listingDTO.getListingImages());
        }

        Listing updatedListing = listingRepository.save(existingListing);
        return convertToDTO(updatedListing);
    }

    public void deleteListing (String listingId){
        Listing existingListing = listingRepository.findById(listingId)
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found"));

        checkAuthentication.validateAuthenticatedUser(existingListing.getHost().getId());

       listingRepository.delete(existingListing);
    }

    //-------Hälp Mäthodz---------
    private ListingResponse convertToDTO (Listing listing){
        ListingResponse response = new ListingResponse();

        response.setListingId(listing.getListingId());
        response.setHostId(listing.getHost().getId());
        response.setHostName(listing.getHost().getUsername());
        response.setListingTitle(listing.getListingTitle());
        response.setListingDescription(listing.getListingDescription());
        response.setGuestLimit(listing.getListingGuestLimit());
        response.setListingPricePerNight(listing.getListingPricePerNight());
        response.setListingImages(listing.getListingImages());

        return response;
    }

    private ListingResponseGetAll convertToDTOGetAll(Listing listing){
        ListingResponseGetAll response = new ListingResponseGetAll();
        response.setListingId(listing.getListingId());
        response.setHostName(listing.getHost().getUsername());
        response.setListingTitle(listing.getListingTitle());
        response.setListingPricePerNight(listing.getListingPricePerNight());
        response.setListingImages(listing.getListingImages());

        return response;
    }

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
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public List<ListingResponse> getListingByHostId(String hostId) {
        User host = userRepository.findById(hostId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return listingRepository.findByHostId(hostId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
