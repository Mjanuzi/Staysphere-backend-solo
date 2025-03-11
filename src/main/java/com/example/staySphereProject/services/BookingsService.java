package com.example.staySphereProject.services;

import com.example.staySphereProject.dto.BookingsDTO;
import com.example.staySphereProject.dto.BookingsResponse;
import com.example.staySphereProject.exeptions.ConflictException;
import com.example.staySphereProject.exeptions.ResourceNotFoundException;
import com.example.staySphereProject.models.Bookings;
import com.example.staySphereProject.models.Listing;
import com.example.staySphereProject.models.User;
import com.example.staySphereProject.repository.BookingsRepository;
import com.example.staySphereProject.repository.ListingRepository;
import com.example.staySphereProject.repository.UserRepository;
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
public class BookingsService {
    private final BookingsRepository bookingsRepository;
    private final ListingRepository listingRepository;
    private final UserRepository userRepository;

    public BookingsService(BookingsRepository bookingsRepository, ListingRepository listingRepository, UserRepository userRepository) {
        this.bookingsRepository = bookingsRepository;
        this.listingRepository = listingRepository;
        this.userRepository = userRepository;
    }

    public BookingsResponse getBookingById(String bookingId) {
        Bookings booking = bookingsRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        return convertToDTO(booking);
    }

    public List<BookingsResponse> getHostBookings(String hostId, String sortOrder) {
        List<Listing> hostListings = listingService.getListingByHostId(hostId);
        List<String> listingIds = hostListings.stream()
                .map(Listing::getListingId)
                .collect(Collectors.toList());
        List<Bookings> bookings = bookingsRepository.findByListingIdIn(listingIds);

        // Sort bookings
        Comparator<Bookings> comparator = Comparator.comparing(Bookings::getStartDate);
        if ("desc".equalsIgnoreCase(sortOrder)) {
            comparator = comparator.reversed();
        }
        bookings.sort(comparator);

        return bookings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<BookingsResponse> getUserBookings(String userId, String sortOrder) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found");
        }
        List<Bookings> bookings = bookingsRepository.findByUserId(userId);

        // Sort bookings
        Comparator<Bookings> comparator = Comparator.comparing(Bookings::getStartDate);
        if ("desc".equalsIgnoreCase(sortOrder)) {
            comparator = comparator.reversed();
        }
        bookings.sort(comparator);

        return bookings.stream()
                .map(this::convertToDTO)
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
        bookingsRepository.deleteById(bookingId);
    }


    public List<BookingsResponse> getAllBookings() {
        List<Bookings> bookings = bookingsRepository.findAll();

        return bookings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public List<BookingsResponse> getUserBookings(String userId) {

        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found");
        }

        List<Bookings> bookings = bookingsRepository.findByUserId(userId);

        return bookings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }



    // Convert Bookings to BookingsResponse DTO

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
        response.setHostName("Kind regards, " + listing.getHost().getUsername());
        response.setBookingDate(booking.getBookingDate());
        response.setStartDate(booking.getStartDate());
        response.setEndDate(booking.getEndDate());
        response.setTotalCost(booking.getTotalCost());
        response.setStatus(booking.isStatus());
        response.setPending(booking.isPending());

        return response;
    }

    @Transactional
    public BookingsResponse createBooking(BookingsDTO bookingsDTO) {

        if (!userRepository.existsById(bookingsDTO.getUserId())) {
            throw new ResourceNotFoundException("User not found");
        }
        if (!listingRepository.existsById(bookingsDTO.getListingId())) {
            throw new ResourceNotFoundException("Listing not found");
        }

        Listing listing = listingRepository.findById(bookingsDTO.getListingId())
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found"));
        //Check Available start end

        LocalDate startDate = bookingsDTO.getStartDate().toInstant()
                .atZone(ZoneId.of("UTC"))
                .toLocalDate().plusDays(1);
        LocalDate endDate = bookingsDTO.getEndDate().toInstant()
                .atZone(ZoneId.of("UTC"))
                .toLocalDate().plusDays(1);

        List<LocalDate> requestedDates = generateDateRange(startDate, endDate);

        if (!listing.getAvailable().containsAll(requestedDates)) {
            throw new ConflictException("Requested dates are not available");
        }

        Bookings booking = new Bookings();
        booking.setUserId(bookingsDTO.getUserId()); // Use DTO getter
        booking.setListingId(bookingsDTO.getListingId());
        booking.setBookingDate(bookingsDTO.getBookingDate());
        booking.setStartDate(bookingsDTO.getStartDate());
        booking.setEndDate(bookingsDTO.getEndDate());
        booking.setBookedDates(requestedDates);
        booking.setStatus(bookingsDTO.isStatus());
        booking.setPending(bookingsDTO.isPending());

        long days = ChronoUnit.DAYS.between(startDate, endDate);
        booking.setTotalCost(days * listing.getListingPricePerNight());

        listing.getAvailable().removeAll(requestedDates);
        listingRepository.save(listing);

        // Save and convert to response
        Bookings savedBooking = bookingsRepository.save(booking);
        return convertToDTO(savedBooking);
    }

    @Transactional
    public BookingsResponse updateBooking(String bookingId, BookingsDTO bookingsDTO) {
        Bookings existingBooking = bookingsRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        Listing listing = listingRepository.findById(existingBooking.getListingId())
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found"));

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
}
