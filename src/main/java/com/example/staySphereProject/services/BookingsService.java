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


    private List<LocalDate> generateDateRange(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> dates = new ArrayList<>();
        LocalDate currentDate = startDate;
        while (currentDate.isBefore(endDate)) {
            dates.add(currentDate);
            currentDate = currentDate.plusDays(1);
        }
        return dates;
    }

    public void deleteBooking(String bookingId) {
        Bookings existingBooking = bookingsRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        bookingsRepository.deleteById(existingBooking.getBookingID());
    }




    /**
     Convert Bookings to BookingsResponse DTO
     Resolve user/listing relationship
     Friendly message to customer
     Combining data from user and listing
    **/
    private BookingsResponse convertToDTO(Bookings booking) {
        BookingsResponse response = new BookingsResponse();
        response.setBookingID(booking.getBookingID());
        response.setUserId(booking.getUserId());

        User user = userRepository.findById(booking.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        response.setListingId(booking.getListingId());
        Listing listing = listingRepository.findById(booking.getListingId())
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found"));

        response.setBookingName(user.getUsername() + ", I would like to wish you a pleasant stay at " + listing.getListingTitle() + "!");
        if(listing instanceof Residence) {
            Residence residence = (Residence) listing;
            response.setHostName("Kind Regards, " + residence.getHost().getUsername());
        }
        response.setBookingDate(booking.getBookingDate());
        response.setStartDate(booking.getStartDate());
        response.setEndDate(booking.getEndDate());
        response.setTotalCost(booking.getTotalCost());
        response.setStatus(booking.isStatus());
        response.setPending(booking.isPending());

        return response;
    }

    /** Will try to explain what we do step by step in createBooking.
        1. Validate that user/listing exist
        2. Convert dates to UTC timezone
        3. Generate date range for start and end
        4. Check if listing got available date.
        5  Calculate cost
        6. Update listing available dates
        7. Save Booking and convert to responseDTO
     **/
    @Transactional


    /**
     Step by step for updateBooking
     1. Restore original dates to availability
     2. Validate new dates against current availability
     3. Update cost calculation
     4. Update and save.
    **/
    @Transactional
    public BookingsResponse updateBooking(String bookingId, BookingsDTO bookingsDTO) {
        Bookings existingBooking = bookingsRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        Listing listing = listingRepository.findById(existingBooking.getListingId())
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found"));

        checkAuthentication.validateAuthenticatedUser(bookingsDTO.getUserId());

        List<LocalDate> originalDates = existingBooking.getBookedDates();

        LocalDate newStart = bookingsDTO.getStartDate().toInstant()
                .atZone(ZoneId.of("UTC"))
                .toLocalDate().plusDays(1);
        LocalDate newEnd = bookingsDTO.getEndDate().toInstant()
                .atZone(ZoneId.of("UTC"))
                .toLocalDate().plusDays(1);

        List<LocalDate> newDates = generateDateRange(newStart, newEnd);

        listing.getAvailable().addAll(originalDates);

        if (!listing.getAvailable().containsAll(newDates)) {
            throw new ConflictException("New dates are not available");
        }


        listing.getAvailable().removeAll(newDates);
        listingRepository.save(listing);

        existingBooking.setBookedDates(newDates); // Update tracked dates
        existingBooking.setBookingDate(bookingsDTO.getBookingDate());
        existingBooking.setStartDate(bookingsDTO.getStartDate());
        existingBooking.setEndDate(bookingsDTO.getEndDate());
        existingBooking.setStatus(bookingsDTO.isStatus());
        existingBooking.setPending(bookingsDTO.isPending());
        existingBooking.setTotalCost(
                ChronoUnit.DAYS.between(newStart, newEnd) * listing.getListingPricePerNight()
        );

        return convertToDTO(bookingsRepository.save(existingBooking));
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
