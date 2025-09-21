package com.example.staySphereProject.services;

import com.example.staySphereProject.converters.BookingDTOConverter;
import com.example.staySphereProject.dto.BookingsDTO;
import com.example.staySphereProject.dto.BookingsResponse;
import com.example.staySphereProject.exeptions.ResourceNotFoundException;
import com.example.staySphereProject.models.Bookings;
import com.example.staySphereProject.models.Listing;
import com.example.staySphereProject.repository.BookingsRepository;
import com.example.staySphereProject.repository.ListingRepository;
import com.example.staySphereProject.util.CheckAuthentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookingsService {

    // Immutable dependencies
    private final BookingsRepository bookingsRepository;
    private final ListingRepository listingRepository;
    private final CheckAuthentication checkAuthentication;
    private final BookingDTOConverter converter;
    private final BookingHandler handler;
    private final BookingQueryService queryService;

    public BookingsService(BookingsRepository bookingsRepository,
                           ListingRepository listingRepository,
                           CheckAuthentication checkAuthentication,
                           BookingDTOConverter converter,
                           BookingHandler handler,
                           BookingQueryService queryService) {
        this.bookingsRepository = bookingsRepository;
        this.listingRepository = listingRepository;
        this.checkAuthentication = checkAuthentication;
        this.converter = converter;
        this.handler = handler;
        this.queryService = queryService;
    }

    public BookingsResponse createBooking(BookingsDTO bookingsDTO) {

        Bookings booking = handler.createStandardBooking(bookingsDTO);

        updateListingAvailability(booking.getListingId(), booking.getBookedDates(), false);

        // Save and convert to response
        Bookings savedBooking = bookingsRepository.save(booking);
        return converter.toResponse(savedBooking);
    }
    // Part of basic crud and direct access
    @Transactional(readOnly = true)
    public BookingsResponse getBookingById(String bookingId) {
        Bookings booking = bookingsRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        checkAuthentication.validateAuthenticatedUser(booking.getUserId());

        return converter.toResponse(booking);
    }
    //Part of basic crud and access for admin roles
    @Transactional(readOnly = true)
    public List<BookingsResponse> getAllBookings() {
        List<Bookings> bookings = bookingsRepository.findAll();

        return bookings.stream()
                .map(converter::toResponse)
                .collect(Collectors.toList());
    }

    // complex BookingQuery therefore we delegate
    @Transactional(readOnly = true)
    public List<BookingsResponse> getBookingsByListingId(String listingId) {
        return queryService.findBookingsByListingId(listingId);
    }

    // complex BookingQuery therefore we delegate
    @Transactional(readOnly = true)
    public List<BookingsResponse> getHostBookings(String hostId, String sortOrder) {
        return queryService.findBookingsByHostId(hostId, sortOrder);
    }

    // complex BookingQuery therefore we delegate
    @Transactional(readOnly = true)
    public List<BookingsResponse> getUserBookings(String userId, String sortOrder) {
        return queryService.findBookingsByUserId(userId, sortOrder);
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
