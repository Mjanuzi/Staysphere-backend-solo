package com.example.staySphereProject.services;


import com.example.staySphereProject.converters.BookingDTOConverter;
import com.example.staySphereProject.dto.BookingsResponse;
import com.example.staySphereProject.exeptions.ResourceNotFoundException;
import com.example.staySphereProject.models.Bookings;
import com.example.staySphereProject.models.Listing;
import com.example.staySphereProject.models.Residence;
import com.example.staySphereProject.repository.BookingsRepository;
import com.example.staySphereProject.repository.ListingRepository;
import com.example.staySphereProject.repository.ResidenceRepository;
import com.example.staySphereProject.repository.UserRepository;
import com.example.staySphereProject.util.CheckAuthentication;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookingQueryService {

    // Immutable dependencies
    private final BookingsRepository bookingsRepository;
    private final UserRepository userRepository;
    private final ListingRepository listingRepository;
    private final ResidenceRepository residenceRepository;
    private final BookingDTOConverter converter;
    private final CheckAuthentication checkAuthentication;


    public BookingQueryService(BookingsRepository bookingsRepository,
                               UserRepository userRepository,
                               ListingRepository listingRepository,
                               ResidenceRepository residenceRepository,
                               BookingDTOConverter converter,
                               CheckAuthentication checkAuthentication) {
        this.bookingsRepository = bookingsRepository;
        this.userRepository = userRepository;
        this.listingRepository = listingRepository;
        this.residenceRepository = residenceRepository;
        this.converter = converter;
        this.checkAuthentication = checkAuthentication;
    }

    public List<BookingsResponse> findBookingsByListingId(String listingId) {
        // Validate listing exists
        validateListingExists(listingId);

        // Get the authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Get the listing to check ownership
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found"));

        // Validate ownership or admin access
        validateListingAccess(listing, currentUsername, authentication);

        // Query bookings for this listing
        List<Bookings> bookings = bookingsRepository.findByListingId(listingId);

        // Convert to response DTOs
        return bookings.stream()
                .map(converter::toResponse)
                .collect(Collectors.toList());
    }

    public List<BookingsResponse> findBookingsByHostId(String hostId, String sortOrder) {
        // Validate host exists and authenticate
        validateUserExists(hostId);
        checkAuthentication.validateAuthenticatedUser(hostId);

        // listings for this host
        List<Residence> hostListings = residenceRepository.findByHostId(hostId);
        List<String> listingIds = hostListings.stream()
                .map(Listing::getListingId)
                .collect(Collectors.toList());

        // bookings for hosts listings
        List<Bookings> bookings = bookingsRepository.findByListingIdIn(listingIds);

        // Sorting method
        List<Bookings> sortedBookings = applySorting(bookings, sortOrder);

        // Convert to response
        return sortedBookings.stream()
                .map(converter::toResponse)
                .collect(Collectors.toList());
    }

    public List<BookingsResponse> findBookingsByUserId(String userId, String sortOrder) {
        // Validate user exists
        validateUserExists(userId);

        // Authenticate user access
        checkAuthentication.validateAuthenticatedUser(userId);

        // Query bookings for this user
        List<Bookings> bookings = bookingsRepository.findByUserId(userId);

        // Sorting method
        List<Bookings> sortedBookings = applySorting(bookings, sortOrder);

        // Convert to response
        return sortedBookings.stream()
                .map(converter::toResponse)
                .collect(Collectors.toList());
    }





    private void validateUserExists(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
    }

    private void validateListingExists(String listingId) {
        if (!listingRepository.existsById(listingId)) {
            throw new ResourceNotFoundException("Listing not found with ID: " + listingId);
        }
    }

    private void validateListingAccess(Listing listing, String currentUsername, Authentication authentication) {
        // Check if user is admin
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        // Check if user is listing owner
        boolean isOwner = false;
        if (listing instanceof Residence) {
            Residence residence = (Residence) listing;
            isOwner = residence.getHost().getUsername().equals(currentUsername);
        }

        // Allow access only if admin or owner
        if (!isAdmin && !isOwner) {
            throw new AccessDeniedException("You do not have permission to view these bookings");
        }
    }

    private List<Bookings> applySorting(List<Bookings> bookings, String sortOrder) {
        Comparator<Bookings> comparator = Comparator.comparing(Bookings::getStartDate);
        if ("desc".equalsIgnoreCase(sortOrder)) {
            comparator = comparator.reversed();
        }

        return bookings.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}
