package com.example.staySphereProject.services;

import com.example.staySphereProject.converters.BookingDTOConverter;
import com.example.staySphereProject.dto.BookingsDTO;
import com.example.staySphereProject.dto.BookingsResponse;
import com.example.staySphereProject.exeptions.ConflictException;
import com.example.staySphereProject.exeptions.ResourceNotFoundException;
import com.example.staySphereProject.models.Bookings;
import com.example.staySphereProject.models.Listing;
import com.example.staySphereProject.models.Residence;
import com.example.staySphereProject.models.User;
import com.example.staySphereProject.repository.BookingsRepository;
import com.example.staySphereProject.repository.ListingRepository;
import com.example.staySphereProject.repository.ResidenceRepository;
import com.example.staySphereProject.repository.UserRepository;
import com.example.staySphereProject.util.CheckAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookingsService {
    private final BookingsRepository bookingsRepository;
    private final ListingRepository listingRepository;
    private final UserRepository userRepository;
    private final CheckAuthentication checkAuthentication;
    private final ResidenceRepository residenceRepository;
    private final BookingDTOConverter converter;
    private final BookingHandler handler;

    public BookingsService(BookingsRepository bookingsRepository,
                           ListingRepository listingRepository,
                           UserRepository userRepository,
                           CheckAuthentication checkAuthentication,
                           ResidenceRepository residenceRepository,
                           BookingDTOConverter converter,
                           BookingHandler handler) {
        this.bookingsRepository = bookingsRepository;
        this.listingRepository = listingRepository;
        this.userRepository = userRepository;
        this.checkAuthentication = checkAuthentication;
        this.residenceRepository = residenceRepository;
        this.converter = converter;
        this.handler = handler;
    }

    public BookingsResponse createBooking(BookingsDTO bookingsDTO) {

        Bookings booking = handler.createStandardBooking(bookingsDTO);

        updateListingAvailability(booking.getListingId(), booking.getBookedDates(), false);

        // Save and convert to response
        Bookings savedBooking = bookingsRepository.save(booking);
        return converter.toResponse(savedBooking);
    }
    @Transactional(readOnly = true)
    public BookingsResponse getBookingById(String bookingId) {
        Bookings booking = bookingsRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        checkAuthentication.validateAuthenticatedUser(booking.getUserId());

        return converter.toResponse(booking);
    }
    @Transactional(readOnly = true)
    public List<BookingsResponse> getAllBookings() {
        List<Bookings> bookings = bookingsRepository.findAll();

        return bookings.stream()
                .map(converter::toResponse)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<BookingsResponse> getBookingsByListingId(String listingId) {
        // Verify listing exists
        if (!listingRepository.existsById(listingId)) {
            throw new ResourceNotFoundException("Listing not found");
        }

        // Get the authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Get the listing to check ownership
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found"));

        // Only allow listing owner or admin to see the bookings
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isOwner = false;
        if(listing instanceof Residence) {
            Residence residence = (Residence) listing;
            isOwner = residence.getHost().getUsername().equals(currentUsername);
        }

        if (!isAdmin && !isOwner) {
            throw new AccessDeniedException("You do not have permission to view these bookings");
        }

        // Get bookings for this listing
        List<Bookings> bookings = bookingsRepository.findByListingId(listingId);

        // Convert to response DTOs using converter
        return bookings.stream()
                .map(converter::toResponse)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<BookingsResponse> getHostBookings(String hostId, String sortOrder) {
        List<Residence> hostListings = residenceRepository.findByHostId(hostId);
        List<String> listingIds = hostListings.stream()
                .map(Listing::getListingId)
                .collect(Collectors.toList());
        List<Bookings> bookings = bookingsRepository.findByListingIdIn(listingIds);

        checkAuthentication.validateAuthenticatedUser(hostId);

        // Sort bookings
        Comparator<Bookings> comparator = Comparator.comparing(Bookings::getStartDate);
        if ("desc".equalsIgnoreCase(sortOrder)) {
            comparator = comparator.reversed();
        }
        bookings.sort(comparator);

        return bookings.stream()
                .map(converter::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BookingsResponse> getUserBookings(String userId, String sortOrder) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found");
        }
        List<Bookings> bookings = bookingsRepository.findByUserId(userId);

        checkAuthentication.validateAuthenticatedUser(userId);

        // Sort bookings
        Comparator<Bookings> comparator = Comparator.comparing(Bookings::getStartDate);
        if ("desc".equalsIgnoreCase(sortOrder)) {
            comparator = comparator.reversed();
        }
        bookings.sort(comparator);

        return bookings.stream()
                .map(converter::toResponse)
                .collect(Collectors.toList());
    }

    public BookingsResponse updateBooking(String bookingId, BookingsDTO bookingsDTO) {
        Bookings existingBooking = bookingsRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        checkAuthentication.validateAuthenticatedUser(bookingsDTO.getUserId());

        // Store original dates for restore
        List<LocalDate> originalDates = existingBooking.getBookedDates();
        String listingId = existingBooking.getListingId();

        // Use handler to handle complex update construct with availability restore
        Bookings updatedBooking = handler.createUpdatedBooking(bookingsDTO, originalDates, listingId);

        // Copy the ID from existing booking
        updatedBooking.setBookingID(existingBooking.getBookingID());

        // Update listing availability by removing new booked dates
        updateListingAvailability(listingId, updatedBooking.getBookedDates(), false);

        // Save updated booking and convert to response
        Bookings savedBooking = bookingsRepository.save(updatedBooking);
        return converter.toResponse(savedBooking);
    }

    public void deleteBooking(String bookingId) {
        Bookings existingBooking = bookingsRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        bookingsRepository.deleteById(existingBooking.getBookingID());
    }

    //Method to help update listings available list by removing once you do a booking
    private void updateListingAvailability(String listingId, List<LocalDate> dates, boolean addDates) {
        if (dates == null || dates.isEmpty()) {
            return;
        }

        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found"));

        if (addDates) {
            listing.getAvailable().addAll(dates);
        } else {
            listing.getAvailable().removeAll(dates);
        }

        listingRepository.save(listing);
    }
}
